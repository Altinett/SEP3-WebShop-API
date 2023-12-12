package sep3.webshop.restapi.services.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import sep3.webshop.restapi.services.messaging.RequestHelper;
import sep3.webshop.restapi.services.messaging.ResponseQueueListener;
import sep3.webshop.restapi.services.messaging.RequestSender;
import sep3.webshop.shared.amqp.RequestMessage;
import sep3.webshop.shared.model.Order;
import sep3.webshop.shared.model.Product;
import sep3.webshop.shared.utils.Printer;
import sep3.webshop.shared.utils.Validator;

import javax.swing.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
@Scope("singleton")
public class OrderDataService {
    private final RequestHelper requestHelper;

    @Autowired
    public OrderDataService(RequestHelper requestHelper) {
        this.requestHelper = requestHelper;
    }

    public List<Order> getOrders() throws IOException {
        CompletableFuture<List<Order>> future = requestHelper.sendRequest("getOrders");

        return requestHelper.handleFuture(future);
    }
    public Order getOrder(int orderId) throws IOException {
        return requestHelper.sendAndHandle("getOrder", orderId);
    }

    public Order createOrder(Order order) throws IOException {
        boolean validEmail = Validator.validEmail(order.getEmail());
        boolean validAddress = Validator.validAddress(order.getAddress());
        boolean validPostCode = Validator.validPostCode(order.getPostcode());
        boolean validPhoneNumber = Validator.validPhoneNumber(order.getPhonenumber());

        boolean emptyFirstname = Validator.isNullOrEmpty(order.getFirstname());
        boolean emptyLastname = Validator.isNullOrEmpty(order.getLastname());
        boolean emptyProducts = Validator.isNullOrEmpty(order.getProducts());
        if (
            emptyProducts || emptyFirstname || emptyLastname ||
            !validEmail || !validAddress || !validPhoneNumber || !validPostCode
        ) {
            return null;
        }
        boolean cityExists = requestHelper.sendAndHandle("cityExists", order.getPostcode());
        if (!cityExists) {
            return null;
        }

        Map<Integer, Integer> orderProductMap = order.getProducts();
        boolean enoughProducts = requestHelper.sendAndHandle("enoughProducts", orderProductMap);
        if (!enoughProducts) {
            return null;
        }

        BigDecimal total = new BigDecimal(0);
        List<Integer> productIds = orderProductMap.keySet().stream().toList();
        List<Product> products = requestHelper.sendAndHandle("getProductsByIds", productIds);
        Printer.print(products);
        for (Product product : products) {
            int orderedAmount = orderProductMap.get(product.getId());
            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(orderedAmount)));
        }
        order.setTotal(total);

        return requestHelper.sendAndHandle("createOrder", order);
    }

}
