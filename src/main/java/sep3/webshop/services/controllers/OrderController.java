package sep3.webshop.services.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    @CrossOrigin
    @GetMapping("/{orderId}")
    public Order getOrder(@PathVariable int orderId) throws SQLException {
        return data.getOrder(orderId);
    }
    @CrossOrigin
    @PostMapping("/order")
    public Order createOrder(@RequestBody Order order) throws SQLException {
        return data.createOrder(order);
    }
}
