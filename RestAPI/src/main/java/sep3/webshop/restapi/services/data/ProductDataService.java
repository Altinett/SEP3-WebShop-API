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
import java.util.concurrent.CompletableFuture;

@Component
@Scope("singleton")
public class ProductDataService {
    private final RequestHelper requestHelper;

    @Autowired
    public ProductDataService(RequestHelper requestHelper) {
        this.requestHelper = requestHelper;
    }

    public List<Product> getProducts() throws IOException {
        CompletableFuture<List<Product>> future = requestHelper.sendRequest("getProducts");
        return requestHelper.handleFuture(future);
    }
    public Product addProduct(Product product) throws IOException {
        CompletableFuture<Product> future = requestHelper.sendRequest("addProduct", product);
        return requestHelper.handleFuture(future);
    }
    public Product getProduct(int id) throws IOException {
        CompletableFuture<Product> future = requestHelper.sendRequest("getProduct", id);
        return requestHelper.handleFuture(future);
    }
    public Product editProduct(Product product) throws IOException {
        CompletableFuture<Product> future = requestHelper.sendRequest("editProduct", product);
        return requestHelper.handleFuture(future);
    }
    public Product removeProduct(int productId) throws IOException {
        CompletableFuture<Product> future = requestHelper.sendRequest("removeProduct", productId);
        return requestHelper.handleFuture(future);
    }
    public List<Product> searchProducts(String query) throws IOException {
        CompletableFuture<List<Product>> future = requestHelper.sendRequest("searchProducts", query);
        return requestHelper.handleFuture(future);
    }
    public List<Product> getProductsByOrderId(int orderId) throws IOException {
        CompletableFuture<List<Product>> future = requestHelper.sendRequest("getProductsByOrderId", orderId);
        return requestHelper.handleFuture(future);
    }

}


