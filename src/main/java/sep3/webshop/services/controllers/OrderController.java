package sep3.webshop.services.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sep3.webshop.services.data.OrderDataService;
import sep3.webshop.shared.model.Order;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderDataService data;

    @Autowired
    public OrderController(OrderDataService data) {
        this.data = data;
    }

    @GetMapping
    public List<Order> getOrders() throws SQLException {
        return data.getOrders();
    }
}
