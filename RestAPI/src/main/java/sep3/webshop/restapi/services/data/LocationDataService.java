package sep3.webshop.restapi.services.data;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sep3.webshop.restapi.services.messaging.RequestHelper;
import sep3.webshop.shared.model.City;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@Scope("singleton")
public class LocationDataService {
    private final RequestHelper requestHelper;

    @Autowired
    public LocationDataService(RequestHelper requestHelper) {
        this.requestHelper = requestHelper;
    }

    public List<City> getCities() throws IOException {
        CompletableFuture<List<City>> cities = requestHelper.sendRequest("getCities");
        return requestHelper.handleFuture(cities);
    }
}

