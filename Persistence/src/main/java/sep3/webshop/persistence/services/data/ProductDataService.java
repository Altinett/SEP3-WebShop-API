package sep3.webshop.persistence.services.data;

import com.rabbitmq.client.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sep3.webshop.persistence.services.messaging.RequestQueueListener;
import sep3.webshop.persistence.services.messaging.ResponseSender;
import sep3.webshop.shared.model.Product;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("singleton")
public class ProductDataService {
    private final DatabaseHelper<Product> helper;

    @Autowired
    public ProductDataService(
            DatabaseHelper<Product> helper,
            RequestQueueListener listener
    ) {
        this.helper = helper;

        listener.on("getProducts",this::getProducts);
        listener.on("addProduct", this::addProduct);
        listener.on("editProduct", this::editProduct);
        listener.on("removeProduct", this::removeProduct);
    }
    public <T> void getProducts(String correlationId, Channel channel, T data) {
        try {
            List<Product> products = getProducts();
            ResponseSender.sendResponse(products, correlationId, channel);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    public <T> void addProduct(String correlationId, Channel channel, T data) {
        try {
            addProduct((Product) data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public <T> void editProduct(String correlationId, Channel channel, T data) {
        try {
            editProduct((Product) data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public <T> void removeProduct(String correlationId, Channel channel, T data) {
        try {
            removeProduct((int) data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Product createProduct(ResultSet rs) throws SQLException {
        Product product = ProductDataService.createProductWithoutCategoryIds(rs);
        try {
            List<Integer> productIds = new ArrayList<>();
            for (String id : rs.getString("category_ids").split(",")) {
                productIds.add(Integer.parseInt(id));
            }
            product.setCategoryIds(productIds);}
        catch (Exception e){e.printStackTrace();}
        return product;
    }
    private static Product createProductWithoutCategoryIds(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        BigDecimal price = rs.getBigDecimal("price");
        int amount = rs.getInt("amount");
        return new Product(id, name, description, price, amount);
    }

    private List<Product> getProducts() throws SQLException {
        return helper.map(
                ProductDataService::createProduct,
                "select p.*, string_agg(PC.category_id::text, ',') AS category_ids from products p " +
                        "join ProductCategories PC on p.id = PC.product_id " +
                        "group by p.id "+
                        "order by p.id " +
                        "limit 40 "
        );
    }
    private Product getProduct(Product product) throws SQLException {
        return helper.mapSingle(
                ProductDataService::createProductWithoutCategoryIds,
                "SELECT * FROM Products WHERE " +
                        "name=? AND " +
                        "description=? AND " +
                        "price=? AND " +
                        "amount=? " +
                        "LIMIT 1",
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getAmount()
        );
    }

    private void addProduct(Product product) throws SQLException {
        helper.executeUpdate(
                "INSERT INTO Products VALUES " +
                        "(default, ?, ?, ?, ?)",
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getAmount()
        );

        // Save copy of category ids
        List<Integer> ids = product.getCategoryIds();

        if (ids == null) return;

        // Populate new product
        product = this.getProduct(product);

        // Add category ids into ProductCategories
        int productId = product.getId();
        int idsLength = ids.size();
        String query = "INSERT INTO ProductCategories VALUES ";
        for (int i = 0; i < idsLength; i++) {
            String endPrefix = i == idsLength-1 ? ";" : ",";
            query += "(" + productId + ", " + ids.get(i) + ")" + endPrefix;
        }
        helper.executeUpdate(query);
    }
    private void removeProduct(int id) throws SQLException {
        helper.executeUpdate("DELETE FROM Products WHERE id=?", id);
    }
    private void editProduct(Product product) throws SQLException {
        helper.executeUpdate(
                "UPDATE Products " +
                        "SET name=?, description=?, price=?, amount=? "+
                        "WHERE id=?",
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getAmount(),
                product.getId()
        );
    }
}
