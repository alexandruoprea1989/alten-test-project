package com.alexandru.alten.model.form;

public class FormProduct {

    private final String name;

    private final Long basePrice;

    private final Long categoryId;

    public FormProduct(String name, Long basePrice, Long categoryId) {
        this.name = name;
        this.basePrice = basePrice;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public Long getBasePrice() {
        return basePrice;
    }

    public Long getCategoryId() {
        return categoryId;
    }
}
