package sep3.webshop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import sep3.webshop.shared.model.Order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = { Sep3WebShopApplicationTests.class, TestConfig.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Sep3WebShopApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testGetOrders() throws JsonProcessingException {
        ResponseEntity<Order[]> response = restTemplate.getForEntity("/orders", Order[].class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());

        ObjectWriter ow = new ObjectMapper().writerWithDefaultPrettyPrinter();
        System.out.println(ow.writeValueAsString(response.getBody()));

    }

}
