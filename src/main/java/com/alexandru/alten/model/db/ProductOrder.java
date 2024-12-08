package com.alexandru.alten.model.db;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany
    private Set<Product> products;

    @ManyToMany
    private Set<Discount> discounts;

    private ProductOrder() {}

    public ProductOrder(Set<Product> products, Set<Discount> discounts) {
        this.products = products;
        this.discounts = discounts;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Set<Discount> getDiscounts() {
        return discounts;
    }

    public Long getId() {
        return id;
    }
}
