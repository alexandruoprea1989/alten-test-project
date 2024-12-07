package com.alexandru.alten.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

record CreateProductForm(String product, String category) {}

record DeleteProductResult(String product, Integer count) {}

record Products(List<String> products) {}

@RestController
public class ProductController {

    private static final Map<String, List<String>> productMap = new HashMap<>(Map.of(
            "meat", new ArrayList<>(List.of("Fish")),
            "vegetable", new ArrayList<>(List.of("Bean", "Carrot"))
    ));

    @PostMapping("/product")
    public String addProduct(@RequestBody CreateProductForm form) {
        String category = form.category();
        String product = form.product();

        // If category already exists
        if (productMap.containsKey(category)) {
            productMap.get(category).add(product);
            return product;
        }

        productMap.put(category, new ArrayList<>(List.of(product)));
        return product;
    }

    @DeleteMapping("/product/{product}")
    public DeleteProductResult deleteProduct(@PathVariable("product") String product) {
        Integer byCategoryCount = productMap.values().stream()
                .map(productList -> productList.remove(product))
                .mapToInt(removed -> removed ? 1 : 0).sum();

        return new DeleteProductResult(product, byCategoryCount);
    }

    @GetMapping("/product")
    public Products getProducts(@RequestParam(value = "category", defaultValue = "") String category) {
        if (category.isBlank()) {
            return new Products(productMap.values().stream().flatMap(List::stream).sorted().toList());
        }

        return new Products(
                Optional.ofNullable(productMap.get(category))
                        .orElse(Collections.emptyList())
                        .stream().sorted().toList()
        );
    }
}
