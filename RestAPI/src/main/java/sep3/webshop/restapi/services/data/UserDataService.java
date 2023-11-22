package sep3.webshop.restapi.services.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sep3.webshop.restapi.services.messaging.RequestHelper;
import sep3.webshop.shared.model.User;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Component
@Scope("singleton")
public class UserDataService {
    private final RequestHelper requestHelper;

    @Autowired
    public UserDataService(RequestHelper requestHelper) {
        this.requestHelper = requestHelper;
    }

    public User getUser(User user) throws IOException {
        CompletableFuture<User> future = requestHelper.sendRequest("getUser", user);
        return requestHelper.handleFuture(future);
    }
}
