package sep3.webshop.persistence.services.data;

import com.rabbitmq.client.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sep3.webshop.persistence.services.messaging.RequestQueueListener;
import sep3.webshop.persistence.services.messaging.ResponseSender;
import sep3.webshop.shared.model.User;

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

        listener.on("getUser", this::getUser);
    }
    public <T> void getUser(String correlationId, Channel channel, T data) {
        try {
            User registeredUser = isRegistered((User) data);
            ResponseSender.sendResponse(registeredUser, correlationId, channel);
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
    public User isRegistered(User user) throws SQLException {
        return helper.mapSingle(
                UserDataService::createUser,
                """
                    SELECT * FROM Admins
                    WHERE username=? AND password=?
                    """,
                user.getUsername(),
                user.getPassword()
        );
    }
}
