package sep3.webshop.restapi.services.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import sep3.webshop.restapi.services.messaging.RequestHelper;
import sep3.webshop.shared.model.Order;
import sep3.webshop.shared.model.Product;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
@Scope("singleton")
public class ProductDataService {
    private final RequestHelper requestHelper;

    @Autowired
    public ProductDataService(RequestHelper requestHelper) {
        this.requestHelper = requestHelper;
    }

    public List<Product> getProducts(Map<String, Object> args) throws IOException {
        return requestHelper.sendAndHandle("getProducts", args);
    }
    public Product addProduct(Product product) throws IOException {
        return requestHelper.sendAndHandle("addProduct", product);
    }
    public Product getProduct(int id) throws IOException {
        return requestHelper.sendAndHandle("getProduct", id);
    }
    public Product editProduct(Product product) throws IOException {
        return requestHelper.sendAndHandle("editProduct", product);
    }
    public Product removeProduct(int productId) throws IOException {
        return requestHelper.sendAndHandle("removeProduct", productId);
    }
    public List<Product> getProductsByOrderId(int orderId) throws IOException {
        return requestHelper.sendAndHandle("getProductsByOrderId", orderId);
    }

}


