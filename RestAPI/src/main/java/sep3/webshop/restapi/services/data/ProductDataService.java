package sep3.webshop.restapi.services.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
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
        CompletableFuture<List<Product>> future = requestHelper.sendRequest("getProducts", null);
        return requestHelper.handleFuture(future);
    }
    public void addProduct(Product product) throws IOException {
        requestHelper.sendRequest("addProduct", product);
    }
    public void editProduct(Product product) throws IOException {
        requestHelper.sendRequest("editProduct", product);
    }
    public void removeProduct(int productId) throws IOException {
        requestHelper.sendRequest("removeProduct", productId);
    }

}


