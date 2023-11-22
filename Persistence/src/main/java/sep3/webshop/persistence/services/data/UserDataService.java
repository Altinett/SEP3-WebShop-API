package sep3.webshop.persistence.services.data;

import com.rabbitmq.client.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sep3.webshop.persistence.services.messaging.RequestQueueListener;
import sep3.webshop.persistence.services.messaging.ResponseSender;
import sep3.webshop.shared.model.Order;
import sep3.webshop.shared.model.User;
import sep3.webshop.shared.utils.Printer;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@Scope("singleton")
public class UserDataService {
    private final DatabaseHelper<User> helper;

    @Autowired
    public UserDataService(
            DatabaseHelper<User> helper,
            RequestQueueListener listener
    ) {
        this.helper = helper;

        listener.on("isAdmin", this::isRegistered);
    }
    public <T> void isRegistered(String correlationId, Channel channel, T data) {
        try {
            User user = (User) data;
            Boolean isRegistered = isRegistered(user.getUsername(), user.getPassword());

            ResponseSender.sendResponse(isRegistered, correlationId, channel);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    public static User createUser(ResultSet rs) throws SQLException {
        return new User(
            rs.getInt("id"),
            rs.getString("username"),
            rs.getString("password"),
            rs.getString("email"),
            rs.getString("firstname"),
            rs.getString("lastname"),
            rs.getDate("birthdate")
        );
    }
    public boolean isRegistered(String username, String password) throws SQLException {
        User user = helper.mapSingle(
                UserDataService::createUser,
            """
                SELECT * FROM Admins
                WHERE username=? AND password=?
                """,
                username,
                password
        );
        return user != null;
    }
}
