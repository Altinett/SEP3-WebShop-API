package sep3.webshop.restapi.services.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import sep3.webshop.restapi.services.messaging.RequestHelper;
import sep3.webshop.restapi.services.messaging.ResponseQueueListener;
import sep3.webshop.restapi.services.messaging.RequestSender;
import sep3.webshop.shared.amqp.RequestMessage;
import sep3.webshop.shared.model.Order;

import javax.swing.*;
import java.io.IOException;
import java.util.List;
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
        CompletableFuture<List<Order>> future = requestHelper.sendRequest("getOrders", null);
        return requestHelper.handleFuture(future);
    }
    public Order getOrder(int orderId) throws IOException {
        CompletableFuture<Object> future = requestHelper.sendRequest("getOrder", orderId);
        return (Order) requestHelper.handleFuture(future);
    }
    public Order createOrder(Order order) throws IOException {
        CompletableFuture<Order> future = requestHelper.sendRequest("createOrder", order);
        return requestHelper.handleFuture(future);
    }

}
