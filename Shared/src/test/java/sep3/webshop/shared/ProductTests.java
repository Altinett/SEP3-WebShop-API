package sep3.webshop.shared;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import sep3.webshop.shared.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
        Assertions.assertNotNull(product);
    }

    @Test
    void testProductGetId() {
        Assertions.assertEquals(sharedProduct.getId(), 1);
    }
    @Test
    void testProductSetId() {
        sharedProduct.setId(2);
        Assertions.assertEquals(sharedProduct.getId(), 2);
    }

    @Test
    void testProductGetName() {
        Assertions.assertEquals(sharedProduct.getName(), "name");
    }

    @Test
    void testProductGetDescription() {
        Assertions.assertEquals(sharedProduct.getDescription(), "description");
    }

    @Test
    void testProductGetPrice() {
        Assertions.assertEquals(sharedProduct.getPrice(), BigDecimal.valueOf(10));
    }
    @Test
    void testProductSetPrice() {
        BigDecimal newPrice = new BigDecimal(20);
        sharedProduct.setPrice(newPrice);
        Assertions.assertEquals(sharedProduct.getPrice(), newPrice);
    }

    @Test
    void testProductGetAmount() {
        Assertions.assertEquals(sharedProduct.getAmount(), 10);
    }
    @Test
    void testProductSetAmount() {
        sharedProduct.setAmount(20);
        Assertions.assertEquals(sharedProduct.getAmount(), 20);
    }

    @Test
    void testProductGetImage() {
        Assertions.assertEquals(sharedProduct.getImage(), "image");
    }

    @Test
    void testProductIsFlagged() {
        Assertions.assertEquals(sharedProduct.isFlagged(), false);
    }

    @Test
    void testProductSetCategoryIds() {
        List<Integer> productCategories = new ArrayList<>();
        productCategories.add(1);
        productCategories.add(2);
        productCategories.add(3);
        sharedProduct.setCategoryIds(productCategories);
        Assertions.assertEquals(sharedProduct.getCategoryIds(), productCategories);
    }

}
