package sep3.webshop.restapi.services.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sep3.webshop.restapi.services.data.OrderDataService;
import sep3.webshop.shared.model.Order;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/orders")
public class OrderController {
    private final OrderDataService data;

    @Autowired
    public OrderController(OrderDataService data) {
        this.data = data;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrders() throws IOException {
        List<Order> orders = data.getOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable int orderId) throws IOException {
        Order order = data.getOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) throws IOException {
        Order createdOrder = data.createOrder(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.OK);
    }

}
