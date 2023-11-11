package sep3.webshop.shared.utils;


import com.rabbitmq.client.Channel;
import sep3.webshop.shared.amqp.RequestMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConcreteRequestSubject implements RequestSubject {
    private final Map<String, List<Observer>> map = new HashMap<>();

    @Override
    public void on(String event, Observer observer) {
        if (map.get(event) == null) {
            List<Observer> observers = new ArrayList<>();
            map.put(event, observers);
        }
        map.get(event).add(observer);
    }

    /*
    @Override
    public void off(String event, Observer observer) {
        if (map.get(event) == null) return;
        map.get(event).remove(observer);
    }
     */

    @Override
    public <T> void emit(String event, String correlationId, Channel channel, RequestMessage<T> data) {
        List<Observer> observers = map.get(event);
        if (observers == null) return;

        for (Observer observer : observers) {
            observer.update(correlationId, channel, data.getPayload());
        }
    }
}
