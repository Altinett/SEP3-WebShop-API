package sep3.webshop.services.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sep3.webshop.shared.model.Order;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@Scope("singleton")
public class OrderDataService {
    private final DatabaseHelper<Order> helper;

    @Autowired
    public OrderDataService(DatabaseHelper<Order> helper) {
        this.helper = helper;
    }

    private static Order createOrder(ResultSet rs) throws SQLException {
        int id = rs.getInt("order_id");
        String fullname = rs.getString("customer_fullname");
        String street = rs.getString("customer_street");
        int postcode = rs.getInt("customer_postcode");
        Date date = rs.getDate("date");
        boolean status = rs.getBoolean("status");
        int total = rs.getInt("total");
        int phoneNumber = rs.getInt("phone_number");
        return new Order(id, fullname, street, postcode, date, status, total, phoneNumber);
    }

    public List<Order> getOrders() throws SQLException {
        return helper.map(
                OrderDataService::createOrder,
                "SELECT * FROM orders LIMIT 40"
        );
    }
}
