package sep3.webshop.shared.amqp;

import java.io.Serializable;

public class RequestMessage<T> implements Serializable {
    private String request, correlationId;
    private T payload;
    public RequestMessage() {}

    public RequestMessage(String request, String correlationId, T payload) {
        this.request = request;
        this.correlationId = correlationId;
        this.payload = payload;
    }

    public String getRequest() {
        return request;
    }
    public String getCorrelationId() {
        return correlationId;
    }
    public T getPayload() {
        return payload;
    }
}
