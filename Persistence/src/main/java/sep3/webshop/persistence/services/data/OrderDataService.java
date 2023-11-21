package sep3.webshop.persistence.services.data;

import com.rabbitmq.client.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sep3.webshop.persistence.services.messaging.ResponseSender;
import sep3.webshop.persistence.services.messaging.RequestQueueListener;
import sep3.webshop.shared.model.Order;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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

        listener.on("getOrder", this::getOrder);
        listener.on("getOrders", this::getOrders);
        listener.on("createOrder", this::createOrder);
    }

    public <T> void getOrder(String correlationId, Channel channel, T data) {
        try {
            Order order = getOrder((int) data);

            ResponseSender.sendResponse(order, correlationId, channel);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    public <T> void getOrders(String correlationId, Channel channel, T data) {
        try {
            List<Order> orders = getOrders();

            ResponseSender.sendResponse(orders, correlationId, channel);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    public <T> void createOrder(String correlationId, Channel channel, T data) {
        try {
            Order createdOrder = createOrder((Order) data);

            ResponseSender.sendResponse(createdOrder, correlationId, channel);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }


    private static Order createOrder(ResultSet rs) throws SQLException {
        Order order = OrderDataService.createOrderWithoutProducts(rs);
        List<Integer> productIds = new ArrayList<>();
        for (String id : rs.getString("product_ids").split(",")) {
            productIds.add(Integer.parseInt(id));
        }
        order.setProductIds(productIds);
        return order;
    }
    private static Order createOrderWithoutProducts(ResultSet rs) throws SQLException {
        int orderId = rs.getInt("id");
        String firstname = rs.getString("firstname");
        String lastname = rs.getString("lastname");
        String address = rs.getString("address");
        int postcode = rs.getInt("postcode");
        boolean status = rs.getBoolean("status");
        int total = rs.getInt("total");
        int phoneNumber = rs.getInt("phonenumber");
        String email = rs.getString("email");
        return new Order(orderId, firstname, lastname, address, postcode, status, total, phoneNumber, email);
    }
    private List<Order> getOrders() throws SQLException {
        return helper.map(
                OrderDataService::createOrder,
                "SELECT o.*, string_agg(op.product_id::text, ',') AS product_ids " +
                        "FROM Orders o " +
                        "JOIN OrderProducts op ON o.id=op.order_id " +
                        "GROUP BY o.id " +
                        "ORDER BY o.id " +
                        "LIMIT 40 "
        );
    }
    private Order getOrder(int orderId) throws SQLException {
        return helper.mapSingle(
                OrderDataService::createOrder,
                "SELECT * FROM ( " +
                    "SELECT o.*, string_agg(op.product_id::text, ',') AS product_ids " +
                    "FROM Orders o " +
                    "JOIN OrderProducts op ON o.id=op.order_id " +
                    "GROUP BY o.id " +
                    "ORDER BY o.id " +
                    ") AS orders " +
                    "WHERE id=? ",
                orderId
        );
    }

    private void updateTotal(int orderId) throws SQLException {
        helper.executeUpdate(
                "UPDATE Orders SET total=(" +
                        "SELECT SUM(P.price * OP.quantity) total "+
                        "FROM Products P JOIN OrderProducts OP " +
                        "ON P.id=OP.product_id AND OP.order_id=?"+
                        ") WHERE id=?",
                orderId,
                orderId
        );
    }

    private Order createOrder(Order order) throws SQLException {
        int id = helper.executeUpdateWithGeneratedKeys(
            "INSERT INTO Orders " +
            "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
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

        Map<Integer, Integer> products = order.getProducts();
        StringBuilder query = new StringBuilder("INSERT INTO OrderProducts VALUES ");
        for (int key : products.keySet()) {
            query.append("(").append(id).append(", ").append(key).append(", ").append(products.get(key)).append("),");
        }
        helper.executeUpdate(query.substring(0, query.length() - 1));

        updateTotal(id);

        return order;
    }

}
