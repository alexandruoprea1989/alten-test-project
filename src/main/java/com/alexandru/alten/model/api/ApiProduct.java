package com.alexandru.alten.model.api;

import com.alexandru.alten.model.db.Category;
import com.alexandru.alten.model.db.Product;

import java.util.List;

public class ApiProduct {

    private final Long id;
    private final String name;
    private final Long basePrice;
    private final List<Long> categoryIds;

    public ApiProduct(Long id, String name, Long basePrice, List<Long> categoryIds) {
        this.id = id;
        this.name = name;
        this.basePrice = basePrice;
        this.categoryIds = categoryIds;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getBasePrice() {
        return basePrice;
    }

    public List<Long> getCategoryIds() {
        return categoryIds;
    }

    public static ApiProduct fromDb(Product product) {
        return new ApiProduct(
                product.getId(),
                product.getName(),
                product.getBasePrice(),
                product.getCategories().stream().map(Category::getId).toList()
        );
    }
}
