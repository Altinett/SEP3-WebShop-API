package sep3.webshop.persistence.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PersistenceTestApplication {

    public static void main(String[] args) {
        // Allow deserialization for message listeners
        System.setProperty("spring.amqp.deserialization.trust.all", "true");

        // Enable test environment
        System.setProperty("spring.profiles.default", "TEST");

        SpringApplication.run(PersistenceTestApplication.class, args);
    }

}
