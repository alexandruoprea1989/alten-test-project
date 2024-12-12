package com.alexandru.alten.model.db;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class StoreUserPrivilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    private List<StoreUserRole> roles;

    private StoreUserPrivilege() {}

    public StoreUserPrivilege(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<StoreUserRole> getRoles() {
        return roles;
    }
}
