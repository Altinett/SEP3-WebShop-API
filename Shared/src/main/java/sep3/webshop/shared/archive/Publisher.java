package sep3.webshop.shared.archive;

import com.rabbitmq.client.*;
import sep3.webshop.shared.utils.ObjectSerializer;

import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.CompletableFuture;


public class Publisher implements Closeable {
    private final Connection connection;
    private final Channel channel;
    private final String name;
    private final BlockingQueue<String> responseQueue;

    public Publisher(String host, String name) throws IOException, TimeoutException {
        this.name = name;

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        connection = factory.newConnection();
        channel = connection.createChannel();

        channel.exchangeDeclare(name, "topic");

        responseQueue = new LinkedBlockingQueue<>();
    }

    public <T> CompletableFuture<T> publishAndReceive(T message, String... topics) throws IOException {
        CompletableFuture<T> completableFuture = new CompletableFuture<>();
        String correlationId = UUID.randomUUID().toString();
        String topic = String.join(".", topics);

        String replyToQueue = channel.queueDeclare().getQueue();

        // Set up a callback for handling the response
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            try {
                T receivedObject = ObjectSerializer.deserialize(delivery.getBody());
                completableFuture.complete(receivedObject);
            } catch (ClassNotFoundException e) {
                completableFuture.completeExceptionally(e);
            }
        };

        // Subscribe to the response queue
        channel.basicConsume(replyToQueue, true, deliverCallback, consumerTag -> {});

        // Publish the message with the correlation ID
        channel.basicPublish(name, topic, new AMQP.BasicProperties.Builder()
                .correlationId(correlationId)
                .replyTo(replyToQueue)
                .build(), ObjectSerializer.serialize(message));

        return completableFuture;
    }

    public <T> void publish(T message, String... topics) throws IOException {
        String topic = String.join(".", topics);
        channel.basicPublish(name, topic, null, ObjectSerializer.serialize(message));
    }

    @Override
    public void close() throws IOException {
        connection.close();
    }
}
