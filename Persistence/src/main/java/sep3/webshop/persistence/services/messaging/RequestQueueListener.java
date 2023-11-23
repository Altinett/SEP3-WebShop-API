package sep3.webshop.persistence.services.messaging;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import sep3.webshop.shared.amqp.RequestMessage;
import sep3.webshop.shared.utils.*;

import java.io.IOException;

@Component
public class RequestQueueListener implements RequestSubject {
    private final ConcreteRequestSubject subject;

    public RequestQueueListener() {
        this.subject = new ConcreteRequestSubject();
    }

    @RabbitListener(queues = "request-queue")
    public void handleOrderRequest(
        byte[] messageBody,
        @Header(AmqpHeaders.CORRELATION_ID) String correlationId,
        Channel channel
    ) throws IOException, ClassNotFoundException {
        RequestMessage<?> requestMessage = ObjectSerializer.deserialize(messageBody);

        Printer.log("[INFO] [" + correlationId + "] REQUESTED " + requestMessage.getRequest());
        Printer.log("[INFO] [" + correlationId + "] PAYLOAD: " + Printer.toString(requestMessage.getPayload()));

        emit(
            requestMessage.getRequest(),
            requestMessage.getCorrelationId(),
            channel,
            requestMessage
        );
    }

    @Override
    public void on(String event, Observer observer) {
        subject.on(event, observer);
    }

    @Override
    public <T> void emit(String event, String correlationId, Channel channel, RequestMessage<T> data) {
        subject.emit(event, correlationId, channel, data);
    }
}
