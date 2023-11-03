package sep3.webshop.services.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sep3.webshop.services.data.ProductDataService;
import sep3.webshop.shared.model.Product;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductDataService data;

    @Autowired
    public ProductController(ProductDataService data) {
        this.data = data;
    }

    @GetMapping
    public List<Product> getProducts() throws SQLException {
        return data.getProducts();
    }

    @PostMapping("/add")
    public void addProduct(@RequestBody Product product) throws SQLException {
        data.addProduct(product);
    }

    @PostMapping("/edit")
    public void editProduct(@RequestBody Product product) throws SQLException {
        data.editProduct(product);
    }

    @PostMapping("/remove")
    public void removeProduct(@RequestParam int id) throws SQLException {
        data.removeProduct(id);
    }
}
