package sep3.webshop.persistence.services.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sep3.webshop.persistence.services.messaging.RequestQueueListener;
import sep3.webshop.persistence.utils.DatabaseHelper;
import sep3.webshop.persistence.utils.Empty;
import sep3.webshop.persistence.utils.RequestHandler;
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
    public ProductDataService(
        DatabaseHelper<Product> helper,
        RequestQueueListener listener
    ) {
        this.helper = helper;

        listener.on("addProduct", RequestHandler.newObserver(this::addProduct));
        listener.on("getProducts", RequestHandler.newObserver(this::getProducts));
        listener.on("editProduct", RequestHandler.newObserver(this::editProduct));
        listener.on("removeProduct", RequestHandler.newObserver(this::removeProduct));
        listener.on("searchProducts", RequestHandler.newObserver(this::searchProducts));
        listener.on("getProductsByOrderId", RequestHandler.newObserver(this::getProductsByOrderId));
    }

    private List<Product> getProductsByOrderId(int orderId) throws SQLException {
        return helper.map(
            ProductDataService::createProduct,
        """
            SELECT
                P.*,
                STRING_AGG(PC.category_id::TEXT, ',') AS category_ids
            FROM Products P
            JOIN ProductCategories PC ON P.id = PC.product_id
            JOIN OrderProducts O ON O.product_id=PC.product_id AND O.order_id=?
            GROUP BY P.id;
            """,
            orderId

        );
    }

    private List<Product> searchProducts(String query) throws SQLException {
        return helper.map(
            ProductDataService::createProduct,
            """
                SELECT
                    P.*,
                    STRING_AGG(PC.category_id::TEXT, ',') AS category_ids,
                    LEVENSHTEIN(LOWER(name), LOWER(?)) / GREATEST(LENGTH(name), LENGTH(?))::FLOAT AS Distance
                FROM Products P
                JOIN ProductCategories PC ON P.id = PC.product_id
                WHERE P.flagged=false
                GROUP BY P.id
                ORDER BY Distance ASC
                LIMIT 20;
                """,
            query, query
        );
    }

    private List<Product> getProducts(Empty empty) throws SQLException {
        return helper.map(
            ProductDataService::createProduct,
        """
            SELECT P.*, STRING_AGG(PC.category_id::text, ',') AS category_ids FROM Products P
            JOIN ProductCategories PC ON P.id = PC.product_id
            WHERE P.flagged=false
            GROUP BY P.id
            ORDER BY P.id
            LIMIT 40
            """
        );
    }

    private Product addProduct(Product product) throws SQLException {
        int id = helper.executeUpdateWithGeneratedKeys(
        """
            INSERT INTO Products VALUES
            (default, ?, ?, ?, ?, ?)
            """,
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getAmount(),
            false
        ).get(0);
        product.setId(id);

        List<Integer> ids = product.getCategoryIds();

        if (ids == null) return product;

        // Add category ids into ProductCategories
        StringBuilder query = new StringBuilder("INSERT INTO ProductCategories VALUES ");
        for (int categoryId : ids) {
            query.append("(").append(id).append(", ").append(categoryId).append("),");
        }
        helper.executeUpdate(query.substring(0, query.length() - 1));

        return product;
    }
    private Product removeProduct(int id) throws SQLException {
        return helper.mapSingle(
                ProductDataService::createProductWithoutCategoryIds,
            "UPDATE Products SET flagged=true WHERE id=? RETURNING *",
            id
        );
    }
    private Product editProduct(Product product) throws SQLException {
        return helper.mapSingle(
                ProductDataService::createProductWithoutCategoryIds,
            """
                UPDATE Products
                SET name=?, description=?, price=?, amount=?
                WHERE id=? RETURNING *
                """,
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getAmount(),
                product.getId()
        );
    }

    private static Product createProduct(ResultSet rs) throws SQLException {
        Product product = ProductDataService.createProductWithoutCategoryIds(rs);

        try {
            List<Integer> productIds = new ArrayList<>();
            for (String id : rs.getString("category_ids").split(",")) {
                productIds.add(Integer.parseInt(id));
            }
            product.setCategoryIds(productIds);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }
    private static Product createProductWithoutCategoryIds(ResultSet rs) throws SQLException {
        return new Product(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getBigDecimal("price"),
                rs.getInt("amount")
        );
    }
}
