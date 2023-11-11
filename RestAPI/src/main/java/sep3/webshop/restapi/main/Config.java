package sep3.webshop.restapi.main;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "sep3")
public class Config {
    @Bean
    public Queue responseQueue() {
        return new Queue("response-queue");
    }

}
