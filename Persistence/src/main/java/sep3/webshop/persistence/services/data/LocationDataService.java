package sep3.webshop.persistence.services.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sep3.webshop.persistence.services.messaging.RequestQueueListener;
import sep3.webshop.persistence.utils.DatabaseHelper;
import sep3.webshop.persistence.utils.Empty;
import sep3.webshop.persistence.utils.RequestHandler;
import sep3.webshop.shared.model.City;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@Scope("singleton")
public class LocationDataService {
    private DatabaseHelper<City> cityHelper;

    @Autowired
    public LocationDataService(
            DatabaseHelper<City> cityHelper,
            RequestQueueListener listener
    ) {
        this.cityHelper = cityHelper;

        listener.on("getCities", RequestHandler.newObserver(this::getCities));
        listener.on("cityExists", RequestHandler.newObserver(this::cityExists));
    }


    private Boolean cityExists(int postcode) throws SQLException {
        return cityHelper.executeQuery(
                "SELECT * FROM Cities WHERE postcode=?",
                postcode
        ).next();
    }

    private List<City> getCities(Empty empty) throws SQLException {
        return cityHelper.map(
                LocationDataService::createCity,
                "SELECT * FROM Cities"
        );
    }


    private static City createCity(ResultSet rs) throws SQLException {
        return new City(
            rs.getString("name"),
            rs.getInt("postcode")
        );
    }
}
