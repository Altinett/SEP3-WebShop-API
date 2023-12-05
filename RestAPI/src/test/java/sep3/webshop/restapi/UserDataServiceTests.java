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
}
