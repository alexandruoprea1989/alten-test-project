package com.alexandru.alten.model.db;

import com.alexandru.alten.model.form.FormProduct;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long basePrice;

    @ManyToMany()
    private List<Category> categories;

    private Product() {}

    public Product(String name, Long basePrice, List<Category> categories) {
        this.name = name;
        this.basePrice = basePrice;
        this.categories = categories;
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

    public List<Category> getCategories() {
        return categories;
    }

    /**
     * Helper that creates a DB model from a form.
     */
    public static Product withFormAndCategories(FormProduct form, List<Category> categories) {
        return new Product(form.getName(), form.getBasePrice(), categories);
    }
}
