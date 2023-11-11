package sep3.webshop.restapi.services.messaging;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sep3.webshop.shared.amqp.RequestMessage;
import sep3.webshop.shared.utils.ObjectSerializer;
import sep3.webshop.shared.utils.Printer;

import java.io.IOException;

@Service
public class RequestSender {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RequestSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public <T> void sendRequest(RequestMessage<T> requestMessage, String correlationId) throws IOException {
        // Prepare message
        byte[] body = ObjectSerializer.serialize(requestMessage);
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setCorrelationId(correlationId);

        Message message = new Message(body, messageProperties);

        // Send message
        rabbitTemplate.send("request-queue", message);
        Printer.log("[INFO] REQUEST SENT: " + correlationId);
    }
}
