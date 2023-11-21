package sep3.webshop.restapi.services.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sep3.webshop.shared.amqp.RequestMessage;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
@Scope("singleton")
public class RequestHelper {
    private final RequestSender requestSender;

    @Autowired
    public RequestHelper(RequestSender requestSender) {
        this.requestSender = requestSender;
    }

    public <T> T handleFuture(CompletableFuture<T> future) {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public <T> CompletableFuture<T> sendRequest(String requestType) throws IOException {
        return sendRequest(requestType, null);
    }
    public <T> CompletableFuture<T> sendRequest(String requestType, T data) throws IOException {
        // Create message
        String correlationId = UUID.randomUUID().toString();
        RequestMessage<T> requestMessage = new RequestMessage<>(requestType, correlationId, data);

        // Send request message
        requestSender.sendRequest(requestMessage, correlationId);

        // CompletableFuture to wait for response
        CompletableFuture<T> completableFuture = new CompletableFuture<>();

        // Listen for response
        ResponseQueueListener.addResponseHandler(correlationId, completableFuture);
        return completableFuture;
    }
}
