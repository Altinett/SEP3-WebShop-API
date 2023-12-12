package sep3.webshop.restapi;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import sep3.webshop.restapi.main.RestApiApplication;
import sep3.webshop.restapi.services.data.OrderDataService;
import sep3.webshop.shared.model.Product;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest(classes = RestApiApplication.class)
class OrderDataServiceTests {
    @Mock private OrderDataService orderDataService;

    @Test
    void contextLoads() {
         assertNotNull(orderDataService);
    }

    private BigDecimal calculateTotalPrice(List<Product> products) {
        BigDecimal total = new BigDecimal(0);
        for (Product product : products) {
            BigDecimal price = product.getPrice();
            BigDecimal amount = BigDecimal.valueOf(product.getAmount());
            total = total.add(price.multiply(amount));
        }
        return total;
    }

    @Test
    void testTotalPriceCalculationTo2Decimals() {
        List<Product> productsFromPersistence = new ArrayList<>();
        Product product1 = new Product();
        product1.setPrice(new BigDecimal(200));
        product1.setAmount(20);
        productsFromPersistence.add(product1);

        Product product2 = new Product();
        product2.setPrice(new BigDecimal(100));
        product2.setAmount(40);
        productsFromPersistence.add(product2);

        Product product3 = new Product();
        product3.setPrice(new BigDecimal("182.459"));
        product3.setAmount(10);
        productsFromPersistence.add(product3);

        BigDecimal total = calculateTotalPrice(productsFromPersistence);

        assertEquals(
                total.setScale(2, BigDecimal.ROUND_HALF_EVEN),
                BigDecimal.valueOf(9824.59)
        );
    }

    @Test
    void testEmptyProductList() {
        List<Product> emptyProductList = new ArrayList<>();
        BigDecimal total = calculateTotalPrice(emptyProductList);
        assertEquals(BigDecimal.ZERO, total);
    }

    @Test
    void testZeroPriceAndAmount() {
        Product product = new Product();
        product.setPrice(BigDecimal.ZERO);
        product.setAmount(0);

        List<Product> productList = new ArrayList<>();
        productList.add(product);

        BigDecimal total = calculateTotalPrice(productList);
        assertEquals(BigDecimal.ZERO, total);
    }

    @Test
    void testNegativePriceAndAmount() {
        Product product = new Product();
        product.setPrice(new BigDecimal(-50));
        product.setAmount(-5);

        List<Product> productList = new ArrayList<>();
        productList.add(product);

        BigDecimal total = calculateTotalPrice(productList);
        assertEquals(BigDecimal.ZERO, total);
    }

    @Test
    void testProductWithNullPrice() {
        Product product = new Product();
        product.setAmount(10);

        List<Product> productList = new ArrayList<>();
        productList.add(product);

        assertThrows(NullPointerException.class, () -> {
            calculateTotalPrice(productList);
        });
    }
}
