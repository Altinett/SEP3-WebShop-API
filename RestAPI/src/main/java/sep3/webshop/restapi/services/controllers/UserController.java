package sep3.webshop.restapi.services.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sep3.webshop.restapi.services.data.UserDataService;
import sep3.webshop.shared.model.User;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
    private final UserDataService data;

    @Autowired
    public UserController(UserDataService data) {
        this.data = data;
    }

    @PostMapping()
    public ResponseEntity<User> getUser(@RequestBody User user) throws IOException {
        User fetchedUser = data.getUser(user);
        if (fetchedUser == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(fetchedUser, HttpStatus.OK);
    }

}
