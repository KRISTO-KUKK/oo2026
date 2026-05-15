package ee.mihkel.veebipood.controller;

import ee.mihkel.veebipood.entity.Product;
import ee.mihkel.veebipood.repository.ProductRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("products")
    public Page<@NonNull Product> getProducts(Pageable pageable,
                                              @RequestParam(defaultValue = "0") Long activeCategoryId) {
        if (activeCategoryId > 0) {
            return productRepository.findByCategoryId(activeCategoryId, pageable);
        }
        return productRepository.findAll(pageable);
    }

    @GetMapping("products/admin")
    public List<Product> getAdminProducts() {
        return productRepository.findAll();
    }

    @GetMapping("products/{id}")
    public Product getOneProduct(@PathVariable Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    @DeleteMapping("products/{id}")
    public List<Product> deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
        return productRepository.findAll();
    }

    @PostMapping("products")
    public List<Product> addProduct(@RequestBody Product product) {
        if (product.getId() != null) {
            throw new RuntimeException("Cannot add with ID");
        }
        productRepository.save(product);
        return productRepository.findAll();
    }

    @PutMapping("products")
    public List<Product> editProduct(@RequestBody Product product) {
        if (product.getId() == null) {
            throw new RuntimeException("Cannot edit without ID");
        }
        if (!productRepository.existsById(product.getId())) {
            throw new RuntimeException("Product ID does not exist");
        }
        productRepository.save(product);
        return productRepository.findAll();
    }
}
