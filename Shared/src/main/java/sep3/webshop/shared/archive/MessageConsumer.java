package sep3.webshop.shared.archive;

import java.io.IOException;

public interface MessageConsumer<T> {
    void notify(T message) throws IOException;
}
