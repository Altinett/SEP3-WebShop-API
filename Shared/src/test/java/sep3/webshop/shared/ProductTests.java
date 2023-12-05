package sep3.webshop.shared;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import sep3.webshop.shared.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTests {

    @Test
    void contextLoads() {
    }

    private Product sharedProduct;
    @BeforeEach
    void beforeEachTest() {
        sharedProduct = new Product(1, "name", "description", BigDecimal.valueOf(10), 10, "image", false);
    }

    @Test
    void testProductCreation() {
        Product product = new Product(1, "name", "description", BigDecimal.valueOf(10), 10, "image", false);
        assertNotNull(product);
    }

    @Test
    void testProductGetId() {
        assertEquals(sharedProduct.getId(), 1);
    }
    @Test
    void testProductSetId() {
        sharedProduct.setId(2);
        assertEquals(sharedProduct.getId(), 2);
    }

    @Test
    void testProductGetName() {
        assertEquals(sharedProduct.getName(), "name");
    }

    @Test
    void testProductGetDescription() {
        assertEquals(sharedProduct.getDescription(), "description");
    }

    @Test
    void testProductGetPrice() {
        assertEquals(sharedProduct.getPrice(), BigDecimal.valueOf(10));
    }
    @Test
    void testProductSetPrice() {
        BigDecimal newPrice = new BigDecimal(20);
        sharedProduct.setPrice(newPrice);
        assertEquals(sharedProduct.getPrice(), newPrice);
    }

    @Test
    void testProductGetAmount() {
        assertEquals(sharedProduct.getAmount(), 10);
    }
    @Test
    void testProductSetAmount() {
        sharedProduct.setAmount(20);
        assertEquals(sharedProduct.getAmount(), 20);
    }

    @Test
    void testProductGetImage() {
        assertEquals(sharedProduct.getImage(), "image");
    }

    @Test
    void testProductIsFlagged() {
        assertEquals(sharedProduct.isFlagged(), false);
    }

    @Test
    void testProductSetAndGetCategoryIds() {
        List<Integer> productCategories = new ArrayList<>();
        productCategories.add(1);
        productCategories.add(2);
        productCategories.add(3);
        sharedProduct.setCategoryIds(productCategories);
        assertEquals(sharedProduct.getCategoryIds(), productCategories);
    }


}
