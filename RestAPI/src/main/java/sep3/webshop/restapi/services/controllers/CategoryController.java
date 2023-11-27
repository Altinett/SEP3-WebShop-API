package sep3.webshop.restapi.services.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sep3.webshop.restapi.services.data.CategoryDataService;
import sep3.webshop.restapi.services.data.OrderDataService;
import sep3.webshop.shared.model.Category;
import sep3.webshop.shared.model.Order;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryDataService data;

    @Autowired
    public CategoryController(CategoryDataService data) {
        this.data = data;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getCategories() throws IOException {
        List<Category> orders = data.getCategories();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
