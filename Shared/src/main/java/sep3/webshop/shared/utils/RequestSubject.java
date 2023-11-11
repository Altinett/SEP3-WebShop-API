package sep3.webshop.shared.utils;

import com.rabbitmq.client.Channel;
import sep3.webshop.shared.amqp.RequestMessage;

public interface RequestSubject {
    void on(String event, Observer observer);
    // void off(String event, Observer observer);
    <T> void emit(String event, String correlationId, Channel channel, RequestMessage<T> data);
}
