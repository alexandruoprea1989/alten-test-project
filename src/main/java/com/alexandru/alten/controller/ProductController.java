package com.alexandru.alten.controller;

import com.alexandru.alten.model.api.ApiProduct;
import com.alexandru.alten.model.db.Product;
import com.alexandru.alten.model.form.FormProduct;
import com.alexandru.alten.store.CategoryRepository;
import com.alexandru.alten.store.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ProductController {

    public record DeleteProductResponse(Boolean success) {}
    public record GetProductsResponse(List<ApiProduct> products) {}

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ProductController(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @PostMapping("/product")
    public ResponseEntity<ApiProduct> createProduct(@RequestBody FormProduct form) {
        // TODO Add form validation
        return categoryRepository.findById(form.getCategoryId()).map(category -> {
            Product productToSave = Product.withFormAndCategories(form, Collections.singletonList(category));
            Product savedProduct = productRepository.save(productToSave);

            return ResponseEntity.ok(ApiProduct.fromDb(savedProduct));
        }).orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<DeleteProductResponse> deleteProduct(@PathVariable("productId") String productId) {
        if (productId == null || !productId.chars().allMatch(Character::isDigit)) {
            return ResponseEntity.badRequest().build();
        }

        Long productIdNum = Long.parseLong(productId);

        if (!productRepository.existsById(productIdNum)) {
            return ResponseEntity.notFound().build();
        }

        productRepository.deleteById(productIdNum);

        return ResponseEntity.ok(new DeleteProductResponse(true));
    }

    @GetMapping("/product")
    public ResponseEntity<GetProductsResponse> getProducts(@RequestParam(value = "categoryId", defaultValue = "") String categoryId) {
        if (categoryId.isBlank()) {
            return ResponseEntity.ok(
                    new GetProductsResponse(
                            productRepository.findAll().stream().map(ApiProduct::fromDb).toList()
                    )
            );
        }
        return categoryRepository
                .findById(Long.parseLong(categoryId))
                .map(List::of)
                .map(productRepository::findByCategories)
                .map(products -> products.stream().map(ApiProduct::fromDb).toList())
                .map(GetProductsResponse::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }
}
