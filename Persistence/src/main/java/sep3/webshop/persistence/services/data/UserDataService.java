package sep3.webshop.persistence.services.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sep3.webshop.persistence.services.messaging.RequestQueueListener;
import sep3.webshop.persistence.utils.DatabaseHelper;
import sep3.webshop.persistence.utils.RequestHandler;
import sep3.webshop.shared.model.User;

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

        listener.on("getUser", RequestHandler.newObserver(this::getUser));
    }
    public User getUser(User user) throws SQLException {
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
    private static User createUser(ResultSet rs) throws SQLException {
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
}
