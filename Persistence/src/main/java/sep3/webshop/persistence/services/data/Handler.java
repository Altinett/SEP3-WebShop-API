package sep3.webshop.persistence.services.data;

import java.sql.SQLException;

public interface Handler<T, R> {
    R execute(T data) throws SQLException;
}
