package com.alexandru.alten.model.db;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class StoreUserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    private List<StoreUserPrivilege> privileges;

    @ManyToMany
    private List<StoreUser> users;

    private StoreUserRole() {}

    public StoreUserRole(String name, List<StoreUserPrivilege> privileges) {
        this.name = name;
        this.privileges = privileges;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<StoreUserPrivilege> getPrivileges() {
        return privileges;
    }

    public List<StoreUser> getUsers() {
        return users;
    }
}
