package sep3.webshop.services.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sep3.webshop.shared.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        int id = rs.getInt("product_id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        double price = rs.getDouble("price");
        int categoryId = rs.getInt("category_id");
        int amount = rs.getInt("amount");
        return new Product(id, name, description, price, categoryId, amount);
    }

    public List<Product> getProducts() throws SQLException {
        return helper.map(
                ProductDataService::createProduct,
                "SELECT * FROM products LIMIT 40"
        );
    }
}

