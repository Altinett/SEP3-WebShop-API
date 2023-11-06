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
    @CrossOrigin
    @PostMapping("/add")
    public void addProduct(@RequestBody Product product) throws SQLException {
        System.out.println("TESTING");
       // System.out.println(data.toString());
        try {
            ObjectWriter ow = new ObjectMapper().writerWithDefaultPrettyPrinter();
            System.out.println(ow.writeValueAsString(product));
        } catch(Exception e) {
            e.printStackTrace();
        }
        System.out.println("TESTINGDONE");
        data.addProduct(product);
    }
    @CrossOrigin
    @PatchMapping("/edit")
    public void editProduct(@RequestBody Product product) throws SQLException {
        data.editProduct(product);
    }
    @CrossOrigin
    @DeleteMapping("/remove")
    public void removeProduct(@RequestParam int id) throws SQLException {
        data.removeProduct(id);
    }
}
