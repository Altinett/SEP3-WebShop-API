package sep3.webshop.shared;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sep3.webshop.shared.model.Category;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CategoryTests {

    private Category sharedCategory;
    @BeforeEach
    void beforeEachTest() {
        sharedCategory = new Category(1, "Gaming");
    }

    @Test
    void testCategoryCreation() {
        Category category = new Category(1, "Gaming");
        assertNotNull(category);
    }
    @Test
    void testGetName() {
        assertEquals(sharedCategory.getName(), "Gaming");
    }
    @Test
    void testGetId() {
        assertEquals(sharedCategory.getId(), 1);
    }


}
