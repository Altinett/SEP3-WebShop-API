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
    public ResponseEntity<List<Product>> getProducts(@RequestParam boolean showFlagged) throws IOException {
        List<Product> products = data.getProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) throws IOException {
        Product product = data.getProduct(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) throws IOException {
        Product addedProduct = data.addProduct(product);
        return new ResponseEntity<>(addedProduct, HttpStatus.OK);
    }

    @PostMapping("/edit")
    public ResponseEntity<Product> editProduct(@RequestBody Product product) throws IOException {
        Product editedProduct = data.editProduct(product);
        return new ResponseEntity<>(editedProduct, HttpStatus.OK);
    }

    @GetMapping("/fromOrder")
    public ResponseEntity<List<Product>> getProductsByOrderId(@RequestParam int id) throws IOException {
        List<Product> products = data.getProductsByOrderId(id);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Product> removeProduct(@RequestParam int id) throws IOException {
        Product product = data.removeProduct(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String query) throws IOException {
        List<Product> products = data.searchProducts(query);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
