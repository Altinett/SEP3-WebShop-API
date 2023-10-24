package sep3.webshop.services.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sep3.webshop.shared.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("singleton")
public class ProductDataService {
    private final DatabaseHelper<Product> helper;

    @Autowired
    public ProductDataService(DatabaseHelper<Product> helper) {
        this.helper = helper;
    }

    private static Product createProduct(ResultSet rs) throws SQLException {
        return new Product(rs.getInt("id"));
    }

    public List<Product> getProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1));
        products.add(new Product(69));
        products.add(new Product(2));
        return products;
        /*
        return helper.map(
                ProductDataService::createProduct,
                "SELECT * FROM products LIMIT 40"
                );
         */
    }
}

