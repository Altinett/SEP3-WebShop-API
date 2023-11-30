package sep3.webshop.persistence.main;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.AliasFor;
import sep3.webshop.persistence.utils.DatabaseHelper;
import sep3.webshop.shared.model.*;

import java.lang.annotation.*;

@Configuration
@ComponentScan(basePackages = "sep3.webshop")
public class Config {
    private static final String JDBC_TEST_URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=Test";
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=WebShop";
    private static final String USERNAME = "postgres", PASSWORD = "1234";

    @Retention(RetentionPolicy.RUNTIME) @Bean @Scope("singleton") public @interface Helper {}

    @Qualifier("ORDER_TEST") @Helper public DatabaseHelper<Order> getOrderTestHelper() {
        return new DatabaseHelper<>(JDBC_TEST_URL, USERNAME, PASSWORD);
    }
    @Qualifier("PRODUCT_TEST") @Helper public DatabaseHelper<Product> getProductTestHelper() {
        return new DatabaseHelper<>(JDBC_TEST_URL, USERNAME, PASSWORD);
    }
    @Qualifier("USER_TEST") @Helper public DatabaseHelper<User> getUserTestHelper() {
        return new DatabaseHelper<>(JDBC_TEST_URL, USERNAME, PASSWORD);
    }
    @Qualifier("CATEGORY_TEST") @Helper public DatabaseHelper<Category> getCategoryTestHelper() {
        return new DatabaseHelper<>(JDBC_TEST_URL, USERNAME, PASSWORD);
    }
    @Qualifier("CITY_TEST") @Helper public DatabaseHelper<City> getCityTestHelper() {
        return new DatabaseHelper<>(JDBC_URL, USERNAME, PASSWORD);
    }

    @Primary @Helper public DatabaseHelper<Order> getOrderHelper() {
        return new DatabaseHelper<>(JDBC_URL, USERNAME, PASSWORD);
    }
    @Primary @Helper public DatabaseHelper<Product> getProductHelper() {
        return new DatabaseHelper<>(JDBC_URL, USERNAME, PASSWORD);
    }
    @Primary @Helper public DatabaseHelper<User> getUserHelper() {
        return new DatabaseHelper<>(JDBC_URL, USERNAME, PASSWORD);
    }
    @Primary @Helper public DatabaseHelper<Category> getCategoryHelper() {
        return new DatabaseHelper<>(JDBC_URL, USERNAME, PASSWORD);
    }
    @Primary @Helper public DatabaseHelper<City> getCityHelper() {
        return new DatabaseHelper<>(JDBC_URL, USERNAME, PASSWORD);
    }

    @Bean
    public Queue requestQueue() {
        return new Queue("request-queue", false);
    }
}
