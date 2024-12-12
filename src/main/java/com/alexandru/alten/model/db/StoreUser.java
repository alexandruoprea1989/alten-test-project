package com.alexandru.alten.model.db;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class StoreUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(optional = true)
    private ProductOrder order;

    @ManyToMany
    private List<StoreUserRole> roles;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private Boolean tokenExpired;

    private StoreUser() {}

    public StoreUser(String username, String password, List<StoreUserRole> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.tokenExpired = false;
    }

    public Long getId() {
        return id;
    }

    public ProductOrder getOrder() {
        return order;
    }

    public List<StoreUserRole> getRoles() {
        return roles;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getTokenExpired() {
        return tokenExpired;
    }
}
