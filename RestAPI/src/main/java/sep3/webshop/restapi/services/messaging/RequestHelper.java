package sep3.webshop.restapi.services.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sep3.webshop.shared.amqp.RequestMessage;
import sep3.webshop.shared.model.Product;

import java.io.IOException;
import java.util.List;
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
    public <T, R> R sendAndHandle(String requestType, T data) throws IOException {
        return handleFuture(sendRequest(requestType, data));
    }
    public <T> CompletableFuture<T> sendRequest(String requestType) throws IOException {
        return sendRequest(requestType, null);
    }
    public <T, R> CompletableFuture<R> sendRequest(String requestType, T data) throws IOException {
        // Create message
        String correlationId = UUID.randomUUID().toString();
        RequestMessage<T> requestMessage = new RequestMessage<>(requestType, correlationId, data);

        // Send request message
        requestSender.sendRequest(requestMessage);

        // CompletableFuture to wait for response
        CompletableFuture<T> completableFuture = new CompletableFuture<>();

        // Listen for response
        ResponseQueueListener.addResponseHandler(correlationId, completableFuture);
        return (CompletableFuture<R>) completableFuture;
    }
}
