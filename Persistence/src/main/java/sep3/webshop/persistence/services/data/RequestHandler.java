package sep3.webshop.persistence.services.data;

import com.rabbitmq.client.Channel;
import sep3.webshop.persistence.services.messaging.ResponseSender;
import sep3.webshop.shared.utils.Observer;

import java.io.IOException;
import java.sql.SQLException;

@FunctionalInterface
interface Handler<T, R> {
    R handle(T data) throws SQLException;
}
public class RequestHandler {
    public static Observer createObserver(Handler handler) {
        return new Observer() {
            @Override public <T> void update(String correlationId, Channel channel, T data) {
                try {
                    ResponseSender.sendResponse(handler.handle(data), correlationId, channel);
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}

