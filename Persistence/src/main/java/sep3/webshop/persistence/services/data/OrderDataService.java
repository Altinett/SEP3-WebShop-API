package sep3.webshop.persistence.services.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sep3.webshop.persistence.services.messaging.RequestQueueListener;
import sep3.webshop.persistence.utils.DatabaseHelper;
import sep3.webshop.persistence.utils.Empty;
import sep3.webshop.persistence.utils.RequestHandler;
import sep3.webshop.shared.model.Order;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Scope("singleton")
public class OrderDataService {
    private final DatabaseHelper<Order> helper;

    @Autowired
    public OrderDataService(
        DatabaseHelper<Order> helper,
        RequestQueueListener listener
    ) {
        this.helper = helper;

        listener.on("getOrder", RequestHandler.newObserver(this::getOrder));
        listener.on("getOrders", RequestHandler.newObserver(this::getOrders));
        listener.on("createOrder", RequestHandler.newObserver(this::newOrder));
    }
    private List<Order> getOrders(Empty empty) throws SQLException {
        return helper.map(
            OrderDataService::createOrder,
        """
                SELECT O.*, ARRAY_AGG(ARRAY[OP.product_id, OP.quantity]) AS products
                FROM Orders O
                JOIN OrderProducts OP ON O.id=OP.order_id
                GROUP BY O.id
                ORDER BY O.id
                LIMIT 40;
                """
        );
    }
    private Order getOrder(int orderId) throws SQLException {
        return helper.mapSingle(
            OrderDataService::createOrder,
        """
            SELECT * FROM (
                SELECT O.*, ARRAY_AGG(ARRAY[OP.product_id, OP.quantity]) AS products
                FROM Orders O
                JOIN OrderProducts OP ON O.id=OP.order_id
                GROUP BY O.id
                ORDER BY O.id
            ) AS orders
            WHERE id=?
            """,
            orderId
        );
    }

    private Order newOrder(Order order) throws SQLException {
        Map<Integer, Integer> products = order.getProducts();
        String delimitedProducts = products.keySet().stream().map(String::valueOf).collect(Collectors.joining(","));

        String updateQuery = "UPDATE Products SET amount = CASE ";
        for (int productId : products.keySet()) {
            int amountToSubtract = products.get(productId);
            updateQuery += " WHEN id=" + productId + " THEN amount-" + amountToSubtract;
        }
        updateQuery += " ELSE amount END WHERE id IN(" + delimitedProducts + ")";

        int id = helper.executeUpdateWithGeneratedKeys(
        """
            INSERT INTO Orders
            VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """,
            order.getFirstname(),
            order.getLastname(),
            order.getAddress(),
            order.getPostcode(),
            order.getDate(),
            false,
            order.getTotal(),
            order.getPhoneNumber(),
            order.getEmail()
        ).get(0);
        order.setOrderId(id);

        String query = "INSERT INTO OrderProducts VALUES ";
        for (int key : products.keySet()) {
            query += "(" + id + ", " + key + ", " + products.get(key) + "),";
        }
        // Remove last comma from query to fix syntax error
        helper.executeUpdate(query.substring(0, query.length() - 1));
        helper.executeUpdate(updateQuery);

        return order;
    }

    private static Order createOrder(ResultSet rs) throws SQLException {
        Map<Integer, Integer> products = new HashMap<>();
        Array arr = rs.getArray("products");
        Integer[][] intArray = (Integer[][]) arr.getArray();
        for(Integer[] row : intArray) {
            products.put(row[0], row[1]);
        }
        Order order = OrderDataService.createOrderWithoutProducts(rs);
        order.setProducts(products);

        return order;
    }
    private static Order createOrderWithoutProducts(ResultSet rs) throws SQLException {
        return new Order(
            rs.getInt("id"),
            rs.getString("firstname"),
            rs.getString("lastname"),
            rs.getString("address"),
            rs.getInt("postcode"),
            rs.getBoolean("status"),
            rs.getBigDecimal("total"),
            rs.getInt("phonenumber"),
            rs.getString("email"),
            rs.getTimestamp("date")
        );
    }
}
