package com.example.dealership.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CarPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    private String photoUrl;
}
