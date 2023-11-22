package sep3.webshop.persistence.services.data;

import com.rabbitmq.client.Channel;
import sep3.webshop.persistence.services.messaging.ResponseSender;
import sep3.webshop.shared.utils.Observer;

import java.io.IOException;
import java.sql.SQLException;

public class RequestHandler {
    public static <T, R> Observer newObserver(Handler<T, R> handler) {
        return new Observer() {
            @Override public <Y> void update(String correlationId, Channel channel, Y data) {
                try {
                    R result = handler.execute((T) data);
                    ResponseSender.sendResponse(result, correlationId, channel);
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}

