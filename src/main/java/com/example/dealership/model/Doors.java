package com.example.dealership.model;

import jakarta.persistence.*;
import lombok.Data;

import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor

public class Doors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer doorCount;

    public Doors(Integer doorCount) {
        this.doorCount = doorCount;
    }

    public Long getId() {
        return id;
    }

    public Integer getDoorCount() {
        return doorCount;
    }


}