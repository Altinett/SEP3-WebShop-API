package sep3.webshop.main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import sep3.webshop.services.data.DatabaseHelper;
import sep3.webshop.shared.model.Order;
import sep3.webshop.shared.model.Product;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "sep3")
public class WebConfig {
    public static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=WebShop";
    public static final String USERNAME = "postgres", PASSWORD = "1234";

    @Bean
    @Scope("singleton")
    public DatabaseHelper<Product> getProductHelper() {
        return new DatabaseHelper<>(JDBC_URL, USERNAME, PASSWORD);
    }


    @Bean
    @Scope("singleton")
    public DatabaseHelper<Order> getOrderHelper() {
        return new DatabaseHelper<>(JDBC_URL, USERNAME, PASSWORD);
    }

}
