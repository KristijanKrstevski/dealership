package com.example.dealership.model;


import jakarta.persistence.*;
import lombok.Data;

import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor

public class Fuel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fuelType;

    public Fuel(String fuelType) {
        this.fuelType = fuelType;
    }

    public Long getId() {
        return id;
    }

    public String getFuelType() {
        return fuelType;
    }


}
