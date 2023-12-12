package sep3.webshop.restapi;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import sep3.webshop.restapi.main.RestApiApplication;
import sep3.webshop.restapi.services.data.UserDataService;
import sep3.webshop.shared.model.User;


@SpringBootTest(classes = RestApiApplication.class)
class UserDataServiceTests {
    @Mock private UserDataService userDataService;

    @Test
    void contextLoads() {
        assertNotNull(userDataService);
    }

    @Test
    void testCredentialsEqualsUserCredentials() {
        User loginInformation = new User("asdghjk1234", "supersecretpassword123");
        User userCredentials = new User("asdghjk1234", "supersecretpassword123");

        assertEquals(loginInformation.getUsername(), userCredentials.getUsername());
        assertEquals(loginInformation.getPassword(), userCredentials.getPassword());
    }

    @Test
    void testNullCredentials() {
        User loginInformation = new User(null, null);
        User userCredentials = new User(null, null);

        assertNull(loginInformation.getUsername());
        assertNull(loginInformation.getPassword());

        assertNull(userCredentials.getUsername());
        assertNull(userCredentials.getPassword());
    }

    @Test
    void testEmptyCredentials() {
        User loginInformation = new User("", "");
        User userCredentials = new User("", "");

        assertEquals("", loginInformation.getUsername());
        assertEquals("", loginInformation.getPassword());

        assertEquals("", userCredentials.getUsername());
        assertEquals("", userCredentials.getPassword());
    }

    @Test
    void testDifferentUsername() {
        User loginInformation = new User("user1", "password123");
        User userCredentials = new User("user2", "password123");

        assertNotEquals(loginInformation.getUsername(), userCredentials.getUsername());
        assertEquals(loginInformation.getPassword(), userCredentials.getPassword());
    }

    @Test
    void testDifferentPassword() {
        User loginInformation = new User("user1", "password123");
        User userCredentials = new User("user1", "differentpassword");

        assertEquals(loginInformation.getUsername(), userCredentials.getUsername());
        assertNotEquals(loginInformation.getPassword(), userCredentials.getPassword());
    }
}
