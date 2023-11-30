package sep3.webshop.persistence.main;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.AliasFor;
import sep3.webshop.persistence.utils.DatabaseHelper;
import sep3.webshop.persistence.utils.Helper;
import sep3.webshop.shared.model.*;

import java.lang.annotation.*;

@Configuration
@ComponentScan(basePackages = "sep3.webshop")
public class Config {
    private static final String JDBC_TEST_URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=Test";
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=WebShop";
    private static final String USERNAME = "postgres", PASSWORD = "1234";

    private <T> DatabaseHelper<T> helper() {
        return new DatabaseHelper<>(JDBC_URL, USERNAME, PASSWORD);
    }
    private <T> DatabaseHelper<T> testHelper() {
        return new DatabaseHelper<>(JDBC_TEST_URL, USERNAME, PASSWORD);
    }

    @Qualifier("ORDER_TEST") @Helper
    public DatabaseHelper<Order> getOrderTestHelper() {
        return testHelper();
    }
    @Qualifier("PRODUCT_TEST") @Helper
    public DatabaseHelper<Product> getProductTestHelper() {
        return testHelper();
    }
    @Qualifier("USER_TEST") @Helper
    public DatabaseHelper<User> getUserTestHelper() {
        return testHelper();
    }
    @Qualifier("CATEGORY_TEST") @Helper
    public DatabaseHelper<Category> getCategoryTestHelper() {
        return testHelper();
    }
    @Qualifier("CITY_TEST") @Helper
    public DatabaseHelper<City> getCityTestHelper() {
        return testHelper();
    }

    @Primary @Helper
    public DatabaseHelper<Order> getOrderHelper() {
        return helper();
    }
    @Primary @Helper
    public DatabaseHelper<Product> getProductHelper() {
        return helper();
    }
    @Primary @Helper
    public DatabaseHelper<User> getUserHelper() {
        return helper();
    }
    @Primary @Helper
    public DatabaseHelper<Category> getCategoryHelper() {
        return helper();
    }
    @Primary @Helper
    public DatabaseHelper<City> getCityHelper() {
        return helper();
    }

    @Bean
    public Queue requestQueue() {
        return new Queue("request-queue", false);
    }
}
