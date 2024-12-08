package com.alexandru.alten.model.db;

import jakarta.persistence.*;

@Entity
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private final String name;

    @Column(nullable = false)
    private final Long flatAmount;

    @Column(nullable = false)
    private final Integer percentAmount;

    public Discount(String name, Long flatAmount, Integer percentAmount) {
        this.name = name;
        this.flatAmount = flatAmount;
        this.percentAmount = percentAmount;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getFlatAmount() {
        return flatAmount;
    }

    public Integer getPercentAmount() {
        return percentAmount;
    }
}
