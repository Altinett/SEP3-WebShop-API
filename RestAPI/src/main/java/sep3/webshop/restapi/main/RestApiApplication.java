package sep3.webshop.restapi.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import sep3.webshop.shared.archive.Publisher;
import sep3.webshop.shared.archive.Subscriber;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class RestApiApplication {

    static Subscriber integerSubscriber;
    static Publisher publisher;
    public static void main(String[] args) throws IOException, TimeoutException {

        /*
        String host = "localhost";
        String exchangeName = "example_exchange";

        try (
                Publisher publiser = new Publisher(host, exchangeName);
                Subscriber subscriber = new Subscriber<String>(host, exchangeName, RestApiApplication::processResponse)
        ) {
            String topic = "example_topic";
            subscriber.subscribe(topic);

            String requestMessage = "Hello World";
            CompletableFuture<String> responseFuture = publiser.publishAndReceive(requestMessage, topic);

            String response = responseFuture.join();
            System.out.println("Received response: " + response);
        }
        integerSubscriber = new Subscriber<Integer>("localhost", "mq", RestApiApplication::Receive);
        integerSubscriber.subscribe("getInteger");


        Subscriber intReceiverSubscriber = new Subscriber<Integer>("localhost", "mq", RestApiApplication::PrintReceive);
        intReceiverSubscriber.subscribe("returnGetInteger");

        publisher = new Publisher("localhost", "mq");
        publisher.publish(0, "getInteger");
        */

        SpringApplication.run(RestApiApplication.class, args);

    }

    private static String processResponse(String request) {
        return "RESPONSE TO: " + request;
    }

    public static void Receive(int value) throws IOException {
        publisher.publish(123, "returnGetInteger");
    }
    public static void PrintReceive(int value) {
        System.out.println("FROM PRINT RECEIVE: " + value);
    }

}
