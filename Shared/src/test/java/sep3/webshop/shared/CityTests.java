package sep3.webshop.shared;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sep3.webshop.shared.model.City;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CityTests {

    private City sharedCity;
    @BeforeEach
    void beforeEachTest() {
        sharedCity = new City("Horsens", 8700);
    }

    @Test
    void testCityCreation() {
        City city = new City("Horsens", 8700);
        assertNotNull(city);
    }
    @Test
    void testGetName() {
        assertEquals(sharedCity.getName(), "Horsens");
    }
    @Test
    void testGetZipcode() {
        assertEquals(sharedCity.getZipcode(), 8700);
    }


}
