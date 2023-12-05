package sep3.webshop.shared;

import org.junit.jupiter.api.Test;
import sep3.webshop.shared.utils.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ValidatorTests {

    @Test
    void testValidEmail() {
        assertTrue(Validator.validEmail("bob@bob.bob"));
    }

    @Test
    void testInvalidEmail() {
        assertFalse(Validator.validEmail("invalid.email@invalid"));
    }

    @Test
    void testValidAddress() {
        assertTrue(Validator.validAddress("123, Kamtjatka"));
    }
    @Test
    void testInvalidAddress() {
        assertFalse(Validator.validAddress("Kamtjatka, 123"));
    }

    @Test
    void testValidPhoneNumber() {
        assertTrue(Validator.validPhoneNumber(12341234));
    }
    @Test
    void testInvalidPhoneNumber() {
        assertFalse(Validator.validPhoneNumber(123));
    }

    @Test
    void testValidPostCode() {
        assertTrue(Validator.validPostCode(5000));
    }
    @Test
    void testInvalidPostCode() {
        assertFalse(Validator.validPostCode(99999));
    }

    @Test
    void testIsNullString() {
        String text = null;
        assertTrue(Validator.isNullOrEmpty(text));
    }
    @Test
    void testIsEmptyString() {
        String text = "";
        assertTrue(Validator.isNullOrEmpty(text));
    }
    @Test
    void testIsNotNullOrEmptyString() {
        assertFalse(Validator.isNullOrEmpty("Hello World"));
    }

    @Test
    void testIsNullList() {
        List<Integer> list = null;
        assertTrue(Validator.isNullOrEmpty(list));
    }
    @Test
    void testIsEmptyList() {
        List<Integer> list = new ArrayList<>();
        assertTrue(Validator.isNullOrEmpty(list));
    }
    @Test
    void testIsNotNullOrEmptyList() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        assertFalse(Validator.isNullOrEmpty(list));
    }

    @Test
    void testIsNullMap() {
        Map<Integer, String> map = null;
        assertTrue(Validator.isNullOrEmpty(map));
    }
    @Test
    void testIsEmptyMap() {
        Map<Integer, String> map = new HashMap<>();
        assertTrue(Validator.isNullOrEmpty(map));
    }
    @Test
    void testIsNotNullOrEmptyMap() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "Hello World");
        assertFalse(Validator.isNullOrEmpty(map));
    }



}
