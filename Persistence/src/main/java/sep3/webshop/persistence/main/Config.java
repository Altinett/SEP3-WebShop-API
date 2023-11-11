package sep3.webshop.persistence.main;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sep3.webshop.persistence.services.data.DatabaseHelper;
import sep3.webshop.shared.model.Order;
import sep3.webshop.shared.model.Product;

@Configuration
@ComponentScan(basePackages = "sep3.webshop")
public class Config {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=WebShop";
    private static final String USERNAME = "postgres", PASSWORD = "1234";

    @Bean
    @Scope("singleton")
    public DatabaseHelper<Order> getOrderHelper() {
        return new DatabaseHelper<>(JDBC_URL, USERNAME, PASSWORD);
    }

    @Bean
    @Scope("singleton")
    public DatabaseHelper<Product> getProductHelper() {
        return new DatabaseHelper<>(JDBC_URL, USERNAME, PASSWORD);
    }

    @Bean
    public Queue requestQueue() {
        return new Queue("request-queue", false);
    }
}
