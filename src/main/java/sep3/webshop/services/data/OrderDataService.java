package sep3.webshop.services.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sep3.webshop.shared.model.Order;

import java.sql.Array;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
@Scope("singleton")
public class OrderDataService {
    private final DatabaseHelper<Order> helper;

    @Autowired
    public OrderDataService(DatabaseHelper<Order> helper) {
        this.helper = helper;
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
        //Date date = rs.getDate("date");
        boolean status = rs.getBoolean("status");
        int total = rs.getInt("total");
        int phoneNumber = rs.getInt("phonenumber");
        String email = rs.getString("email");
        return new Order(orderId, firstname, lastname, address, postcode, status, total, phoneNumber, email);
    }

    public List<Order> getOrders() throws SQLException {
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
    public Order getOrder(int orderId) throws SQLException {
        return helper.mapSingle(
                OrderDataService::createOrder,
                "SELECT * ( " +
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

    private Order getOrder(Order order) throws SQLException {
        return helper.mapSingle(
                OrderDataService::createOrderWithoutProducts,
                "SELECT * FROM Orders o WHERE "+
                "firstname=? AND " +
                "lastname=? AND " +
                "address=? AND " +
                "postcode=? AND " +
                "date=? AND " +
                "total=? AND " +
                "phonenumber=? AND " +
                "email=? " +
                "ORDER BY o.id DESC " +
                "LIMIT 1",
                order.getFirstname(),
                order.getLastname(),
                order.getAddress(),
                order.getPostcode(),
                order.getDate(),
                order.getTotal(),
                order.getPhoneNumber(),
                order.getEmail()
        );
    }

    public Order createOrder(Order order) throws SQLException {
        try {
            ObjectWriter ow = new ObjectMapper().writerWithDefaultPrettyPrinter();
            System.out.println(ow.writeValueAsString(order));
            System.out.println(order.getDate());
        } catch(Exception e) {}
        helper.executeUpdate(
            "INSERT INTO Orders "+
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
        );

        // Save copy of product ids
        List<Integer> ids = order.getProductIds();

        // Populate new order

        order = this.getOrder(order);
        try {
            ObjectWriter ow = new ObjectMapper().writerWithDefaultPrettyPrinter();
            System.out.println(ow.writeValueAsString(order));
        } catch(Exception e) {}
        order.setProductIds(ids);

        // Add product ids into orderproducts
        int orderId = order.getOrderId();
        int idsLength = ids.size();
        String query = "INSERT INTO OrderProducts VALUES ";
        for (int i = 0; i < idsLength; i++) {
            String endPrefix = i == idsLength-1 ? ";" : ",";
            query += "(" + orderId + ", " + ids.get(i) + ")" + endPrefix;
        }
        helper.executeUpdate(query);

        return order;
    }
}
