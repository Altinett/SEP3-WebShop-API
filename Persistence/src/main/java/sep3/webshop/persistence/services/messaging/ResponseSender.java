package sep3.webshop.persistence.services.messaging;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sep3.webshop.shared.utils.ObjectSerializer;
import sep3.webshop.shared.utils.Printer;

import java.io.IOException;

@Component
@Scope("singleton")
public class ResponseSender {
    public static void sendResponse(Object obj, String correlationId, Channel channel) throws IOException {
        channel.basicPublish("", "response-queue",
                new AMQP.BasicProperties.Builder()
                        .correlationId(correlationId)
                        .build(),
                ObjectSerializer.serialize(obj));
        Printer.log("[INFO] [" + correlationId + "] RESPONSE SENT");
    }
}
