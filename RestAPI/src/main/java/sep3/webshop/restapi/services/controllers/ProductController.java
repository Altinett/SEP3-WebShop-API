package sep3.webshop.restapi.services.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sep3.webshop.restapi.services.data.ProductDataService;
import sep3.webshop.shared.model.Product;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/products")
public class ProductController {
    private final ProductDataService data;

    @Autowired
    public ProductController(ProductDataService data) {
        this.data = data;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() throws IOException {
        List<Product> products = data.getProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/add")
    public void addProduct(@RequestBody Product product) throws IOException {
        data.addProduct(product);
    }

    @PostMapping("/edit")
    public void editProduct(@RequestBody Product product) throws IOException {
        data.editProduct(product);
    }

    @DeleteMapping("/remove")
    public void removeProduct(@RequestParam int id) throws IOException {
        data.removeProduct(id);
    }
}
