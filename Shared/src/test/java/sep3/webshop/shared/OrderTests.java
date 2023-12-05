package sep3.webshop.shared;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sep3.webshop.shared.model.Order;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderTests {

    @Test
    void contextLoads() {
    }

    private Order sharedOrder;
    private Timestamp now = new Timestamp(System.currentTimeMillis());
    @BeforeEach
    void beforeEachTest() {
        sharedOrder = new Order(1, "firstname", "lastname", "123,address", 8700, false, BigDecimal.valueOf(10), 12345678, "bob@bob.bob", now);
    }

    @Test
    void testProductCreation() {
        sharedOrder = new Order(1, "firstname", "lastname", "123,address", 8700, false, BigDecimal.valueOf(10), 12345678, "bob@bob.bob", now);
        assertNotNull(sharedOrder);
    }

    @Test
    void testOrderGetId() {
        assertEquals(sharedOrder.getOrderId(), 1);
    }
    @Test
    void testOrderSetId() {
        sharedOrder.setOrderId(2);
        assertEquals(sharedOrder.getOrderId(), 2);
    }

    @Test
    void testOrderGetFirstname() {
        assertEquals(sharedOrder.getFirstname(), "firstname");
    }
    @Test
    void testOrderGetLastname() {
        assertEquals(sharedOrder.getLastname(), "lastname");
    }
    @Test
    void testOrderGetAddress() {
        assertEquals(sharedOrder.getAddress(), "123,address");
    }
    @Test
    void testOrderGetPostcode() {
        assertEquals(sharedOrder.getPostcode(), 8700);
    }
    @Test
    void testOrderGetStatus() {
        assertEquals(sharedOrder.getStatus(), false);
    }
    @Test
    void testOrderGetTotal() {
        assertEquals(sharedOrder.getTotal(), BigDecimal.valueOf(10));
    }
    @Test
    void testOrderSetTotal() {
        BigDecimal newTotal = new BigDecimal(20);
        sharedOrder.setTotal(newTotal);
        assertEquals(sharedOrder.getTotal(), newTotal);
    }
    @Test
    void testOrderGetPhoneNumber() {
        assertEquals(sharedOrder.getPhoneNumber(), 12345678);
    }
    @Test
    void testOrderGetEmail() {
        assertEquals(sharedOrder.getEmail(), "bob@bob.bob");
    }
    @Test
    void testOrderGetDate() {
        assertEquals(sharedOrder.getDate(), now);
    }
    @Test
    void testOrderSetAndGetProducts() {
        Map<Integer, Integer> products = new HashMap<>();
        products.put(1, 1);
        products.put(2, 2);
        products.put(3, 3);

        sharedOrder.setProducts(products);
        assertEquals(sharedOrder.getProducts(), products);
    }

}
