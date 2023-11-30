package sep3.webshop.restapi.services.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sep3.webshop.restapi.services.data.LocationDataService;
import sep3.webshop.shared.model.City;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/location")
public class LocationController {
    private final LocationDataService data;

    @Autowired
    public LocationController(LocationDataService data) {
        this.data = data;
    }

    @GetMapping("/cities")
    public ResponseEntity<List<City>> getCities() throws IOException {
        List<City> categories = data.getCities();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
