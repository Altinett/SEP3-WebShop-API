package sep3.webshop.persistence.services.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sep3.webshop.persistence.services.messaging.RequestQueueListener;
import sep3.webshop.persistence.utils.*;
import sep3.webshop.shared.model.Product;
import sep3.webshop.shared.utils.Printer;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        listener.on("getProduct", RequestHandler.newObserver(this::getProduct));
        listener.on("getProducts", RequestHandler.newObserver(this::getProducts));
        listener.on("editProduct", RequestHandler.newObserver(this::editProduct));
        listener.on("removeProduct", RequestHandler.newObserver(this::removeProduct));
        listener.on("getProductsByOrderId", RequestHandler.newObserver(this::getProductsByOrderId));
    }

    private Product getProduct(int id) throws SQLException {
        return helper.mapSingle(
            ProductDataService::createProduct,
            "SELECT * FROM Products WHERE id=?",
            id
        );
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

    private List<Product> getProducts(Map<String, Object> args) throws SQLException {
        Integer min = (Integer) args.get("min");
        Integer max = (Integer) args.get("max");
        String query = (String) args.get("query");
        Boolean showFlagged = (Boolean) args.get("showFlagged");
        //List<Integer> categories = (List<Integer>) args.get("categories");
        int[] categories = ((List<Integer>) args.get("categories")).stream().mapToInt(Integer::intValue).toArray();

        // Pagination
        Integer page = (Integer) args.get("page");
        Integer pageSize = (Integer) args.get("pageSize");

        int offset = (page - 1) * pageSize;

        String sqlQuery = """
                SELECT
                    P.*,
                    STRING_AGG(PC.category_id::text, ',') AS category_ids,
                    LEVENSHTEIN(LOWER(name), LOWER(?)) / GREATEST(LENGTH(name), LENGTH(?))::FLOAT AS Distance
                FROM Products P
                JOIN ProductCategories PC ON P.id = PC.product_id
                WHERE
                    -- Whether to show/hide flagged products
                    (? OR (NOT ? AND P.flagged = FALSE))
                    AND
                    (COALESCE(ARRAY_LENGTH(?, 1), 0) = 0 OR (
                        NOT COALESCE(ARRAY_LENGTH(?, 1), 0) = 0 AND
                            ARRAY(SELECT category_id FROM ProductCategories WHERE product_id=P.id) && ?
                        )
                    )
                GROUP BY P.id
            """;

        if (query.isEmpty()) {
            sqlQuery += " ORDER BY P.id";
        } else {
            sqlQuery += " ORDER BY Distance";
        }
        sqlQuery += " OFFSET ? LIMIT ?";

        List<Product> products = helper.map(
            ProductDataService::createProduct,
            sqlQuery,
            query, query,
            showFlagged, showFlagged,
            categories, categories, categories,
            offset, pageSize
        );
        products = ProductFilter.filterProducts(
                products,
                //ProductFilter.categoryFilter(categories),
                ProductFilter.priceRangeFilter(min, max)
        );
        return products;
    }

    private Product addProduct(Product product) throws SQLException {
        int id = helper.executeUpdateWithGeneratedKeys(
        """
            INSERT INTO Products VALUES
            (default, ?, ?, ?, ?, ?, ?)
            """,
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getAmount(),
            false,
            ImageResizer.resizeBase64Image(product.getImage(), 400)
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
                SET name=?, description=?, price=?, amount=?, image=?, flagged=?
                WHERE id=? RETURNING *
                """,
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getAmount(),
                product.getImage(),
                product.isFlagged(),
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
                rs.getInt("amount"),
                rs.getString("image"),
                rs.getBoolean("flagged")
        );
    }
}
