package com.alexandru.alten.model.db;

import jakarta.persistence.*;

@Entity
public class StoreUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(optional = true)
    private ProductOrder order;

    @Column(nullable = false)
    private String name;

    private StoreUser() {}

    public StoreUser(String name) {
        this.name = name;
    }

    public StoreUser(String name, ProductOrder order) {
        this.name = name;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public ProductOrder getOrder() {
        return order;
    }

    public String getName() {
        return name;
    }
}
