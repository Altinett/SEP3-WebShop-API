package sep3.webshop.restapi.services.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sep3.webshop.restapi.services.data.ProductDataService;
import sep3.webshop.shared.model.Product;
import sep3.webshop.shared.utils.Printer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<List<Product>> getProducts(
        // Filtering
        @RequestParam(required = false, defaultValue = "true") boolean showFlagged,
        @RequestParam(required = false, defaultValue = "") List<Integer> categories,
        @RequestParam(required = false, defaultValue = "") String query,
        @RequestParam(required = false, defaultValue = "") Integer max,
        @RequestParam(required = false, defaultValue = "") Integer min,

        // Pagination
        @RequestParam(required = false, defaultValue = "15") Integer pageSize,
        @RequestParam(required = false, defaultValue = "1") Integer page
    ) throws IOException {
        Map<String, Object> args = new HashMap<>();
        args.put("showFlagged", showFlagged);
        args.put("categories", categories);
        args.put("query", query);
        args.put("min", min);
        args.put("max", max);
        args.put("pageSize", pageSize);
        args.put("page", page);

        Printer.print(args);

        List<Product> products = data.getProducts(args);
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
}
