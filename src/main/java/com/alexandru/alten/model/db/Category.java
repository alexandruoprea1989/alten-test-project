package com.alexandru.alten.model.db;

import com.alexandru.alten.model.form.FormCategory;
import jakarta.persistence.*;

import java.util.Collections;
import java.util.Set;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany
    private Set<Product> products;

    @Column(nullable = false, unique = true)
    private String name;

    private Category() {}

    public Category(String name, Set<Product> products) {
        this.name = name;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public String getName() {
        return name;
    }

    /**
     * Helper that creates a DB model from a form.
     */
    public static Category withForm(FormCategory form) {
        return new Category(form.getName(), Collections.emptySet());
    }
}
