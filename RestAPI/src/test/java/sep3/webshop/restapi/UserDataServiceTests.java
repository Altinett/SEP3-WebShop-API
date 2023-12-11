package sep3.webshop.restapi;

import org.junit.jupiter.api.Assertions;
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
        Assertions.assertNotNull(userDataService);
    }

    @Test
    void testCredentialsEqualsUserCredentials() {
        User loginInformation = new User("asdghjk1234", "supersecretpassword123");
        User userCredentials = new User("asdghjk1234", "supersecretpassword123");

        Assertions.assertEquals(loginInformation.getUsername(), userCredentials.getUsername());
        Assertions.assertEquals(loginInformation.getPassword(), userCredentials.getPassword());
    }

    @Test
    void testNullCredentials() {
        User loginInformation = new User(null, null);
        User userCredentials = new User(null, null);

        Assertions.assertNull(loginInformation.getUsername());
        Assertions.assertNull(loginInformation.getPassword());

        Assertions.assertNull(userCredentials.getUsername());
        Assertions.assertNull(userCredentials.getPassword());
    }

    @Test
    void testEmptyCredentials() {
        User loginInformation = new User("", "");
        User userCredentials = new User("", "");

        Assertions.assertEquals("", loginInformation.getUsername());
        Assertions.assertEquals("", loginInformation.getPassword());

        Assertions.assertEquals("", userCredentials.getUsername());
        Assertions.assertEquals("", userCredentials.getPassword());
    }

    @Test
    void testDifferentUsername() {
        User loginInformation = new User("user1", "password123");
        User userCredentials = new User("user2", "password123");

        Assertions.assertNotEquals(loginInformation.getUsername(), userCredentials.getUsername());
        Assertions.assertEquals(loginInformation.getPassword(), userCredentials.getPassword());
    }

    @Test
    void testDifferentPassword() {
        User loginInformation = new User("user1", "password123");
        User userCredentials = new User("user1", "differentpassword");

        Assertions.assertEquals(loginInformation.getUsername(), userCredentials.getUsername());
        Assertions.assertNotEquals(loginInformation.getPassword(), userCredentials.getPassword());
    }
}
