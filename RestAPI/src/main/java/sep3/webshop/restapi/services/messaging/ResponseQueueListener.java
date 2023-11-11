
package sep3.webshop.restapi.services.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import sep3.webshop.shared.utils.ObjectSerializer;
import sep3.webshop.shared.utils.Printer;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ResponseQueueListener {
    private static final Map<String, CompletableFuture<Object>> responseHandlers = new ConcurrentHashMap<>();

    @RabbitListener(queues = "response-queue")
    public void handleOrderResponse(
        byte[] messageBody,
        @Header(AmqpHeaders.CORRELATION_ID) String correlationId
    ) throws IOException, ClassNotFoundException {
        Printer.log("[INFO] RESPONSE RECEIVED: " + correlationId);

        CompletableFuture<Object> completableFuture = responseHandlers.remove(correlationId);
        if (completableFuture == null) return;

        completableFuture.complete(ObjectSerializer.deserialize(messageBody));
    }

    public static void addResponseHandler(String correlationId, Object completableFuture) {
        responseHandlers.put(correlationId, (CompletableFuture<Object>) completableFuture);
    }
}