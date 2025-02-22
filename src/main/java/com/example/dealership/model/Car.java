package com.example.dealership.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
//@NoArgsConstructor

public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;
    private int kw;
    private int year;

    @ManyToOne
    @JoinColumn(name = "fuel_id")
    private Fuel fuel;

    private String color;

    @ManyToOne
    @JoinColumn(name = "doors_id")
    private Doors doors;

    private int km;
    private double price;
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CarPhoto> photos;
    public Car() {
    }
    public Car(String brand, String model, int kw, int year, Fuel fuel, String color, Doors doors, int km, double price) {
        this.brand = brand;
        this.model = model;
        this.kw = kw;
        this.year = year;
        this.fuel = fuel;
        this.color = color;
        this.doors = doors;
        this.km = km;
        this.price = price;
    }

    public void setFuel(Fuel fuel) {
        this.fuel = fuel;
    }

    public void setDoors(Doors doors) {
        this.doors = doors;
    }
    public List<CarPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<CarPhoto> photos) {
        this.photos = photos;
    }
}