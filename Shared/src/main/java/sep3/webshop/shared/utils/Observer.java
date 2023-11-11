package sep3.webshop.shared.utils;

import com.rabbitmq.client.Channel;
import sep3.webshop.shared.amqp.RequestMessage;

public interface Observer {
    <T> void update(String correlationId, Channel channel, T data);
}
