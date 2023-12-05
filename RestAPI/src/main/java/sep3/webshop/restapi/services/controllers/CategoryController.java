package sep3.webshop.restapi.services.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sep3.webshop.restapi.services.data.CategoryDataService;
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
        List<Category> categories = data.getCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable int id) throws IOException {
        Category category = data.getCategory(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping("/edit")
    public ResponseEntity<Category> editCategory(@RequestBody Category category) throws IOException {
        Category editedCategory = data.editCategory(category);
        return new ResponseEntity<>(editedCategory, HttpStatus.OK);
    }
}
