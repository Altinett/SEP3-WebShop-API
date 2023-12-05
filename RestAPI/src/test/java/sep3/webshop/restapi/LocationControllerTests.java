package sep3.webshop.restapi;

import static org.junit.Assert.assertNotNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import sep3.webshop.restapi.main.RestApiApplication;
import sep3.webshop.restapi.services.data.LocationDataService;
import sep3.webshop.shared.model.City;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.List;


@SpringBootTest(classes = RestApiApplication.class)
@AutoConfigureMockMvc
class LocationControllerTests {

    @Autowired private MockMvc mockMvc;
    @Mock private LocationDataService locationDataService;


    @Test
    void contextLoads() {
        assertNotNull(locationDataService);
    }
}
