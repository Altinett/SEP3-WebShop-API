package sep3.webshop.shared.archive;

import com.rabbitmq.client.*;
import sep3.webshop.shared.utils.ObjectSerializer;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Subscriber<T> implements Closeable {
    private final String name;
    private final Connection connection;
    private final Channel channel;
    private final String queueName;

    public Subscriber(String host, String name, MessageConsumer<T> consumer) throws IOException, TimeoutException {
        this.name = name;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        connection = factory.newConnection();
        channel = connection.createChannel();
        queueName = channel.queueDeclare().getQueue();

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            byte[] serializedObject = delivery.getBody();
            String correlationId = delivery.getProperties().getCorrelationId();

            try {
                T receivedObject = ObjectSerializer.deserialize(serializedObject);
                respondToMessage(receivedObject, "Hello Rabbitmq", correlationId);

            } catch (ClassNotFoundException e) {
                System.out.println("Something went wrong trying to deserialize the object");
                System.out.println(e);
            }
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
    }

    public void subscribe(String topic) throws IOException {
        channel.queueBind(queueName, name, "#." + topic + ".#");
    }

    public <R> void respondToMessage(T request, R response, String correlationId) throws IOException {
        channel.basicPublish(name, "example_topic", new AMQP.BasicProperties.Builder()
                .correlationId(correlationId)
                .build(), ObjectSerializer.serialize(response));

    }

    @Override
    public void close() throws IOException {
        channel.queueDelete(queueName);
        connection.close();
    }
}
