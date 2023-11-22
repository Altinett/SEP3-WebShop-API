package sep3.webshop.persistence.utils;

import java.sql.SQLException;

public interface Handler<T, R> {
    R execute(T data) throws SQLException;
}
