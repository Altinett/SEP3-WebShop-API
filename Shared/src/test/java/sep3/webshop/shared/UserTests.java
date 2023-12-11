package sep3.webshop.shared;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sep3.webshop.shared.model.User;

import java.sql.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserTests {

    private User sharedUser;
    private Date dob = new Date(2000, 2, 20);
    @BeforeEach
    void beforeEachTest() {
        sharedUser = new User(1, "username", "password", "email", "firstname", "lastname", dob);
    }

    @Test
    void testUserCreation() {
        User user = new User(1, "username", "password", "email", "firstname", "lastname", dob);
        assertNotNull(user);
    }

    @Test
    void testGetId() {
        assertEquals(sharedUser.getId(), 1);
    }
    @Test
    void testSetId() {
        sharedUser.setId(2);
        assertEquals(sharedUser.getId(), 2);
    }
    @Test
    void testGetIdZero() {
        sharedUser.setId(0);
        assertEquals(sharedUser.getId(), 0);
    }
    @Test
    void testGetIdLargeValue() {
        sharedUser.setId(Integer.MAX_VALUE);
        assertEquals(sharedUser.getId(), Integer.MAX_VALUE);
    }

    @Test
    void testGetUsername() {
        assertEquals(sharedUser.getUsername(), "username");
    }
    @Test
    void testGetPassword() {
        assertEquals(sharedUser.getPassword(), "password");
    }
    @Test
    void testGetEmail() {
        assertEquals(sharedUser.getEmail(), "email");
    }
    @Test
    void testGetFirstname() {
        assertEquals(sharedUser.getFirstname(), "firstname");
    }
    @Test
    void testGetLastname() {
        assertEquals(sharedUser.getLastname(), "lastname");
    }
    @Test
    void testGetDate() {
        assertEquals(sharedUser.getDob(), dob);
    }

}
