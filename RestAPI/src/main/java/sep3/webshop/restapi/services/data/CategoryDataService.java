package sep3.webshop.restapi.services.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import sep3.webshop.restapi.services.messaging.RequestHelper;
import sep3.webshop.shared.model.Category;
import sep3.webshop.shared.model.Order;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@Scope("singleton")
public class CategoryDataService {
    private final RequestHelper requestHelper;

    @Autowired
    public CategoryDataService(RequestHelper requestHelper) {
        this.requestHelper = requestHelper;
    }

    public List<Category> getCategories() throws IOException {
        CompletableFuture<List<Category>> future = requestHelper.sendRequest("getCategories");
        return requestHelper.handleFuture(future);
    }

}
