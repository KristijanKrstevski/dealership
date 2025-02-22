package com.example.dealership.service;


import com.example.dealership.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {
    List<Car> findAll();
    Optional<Car> findById(Long id);
    Car save(Car car);
    void deleteById(Long id);
    Car update(Car car);
    List<Car> findByBrand(String brand);
    List<Car> findByFuelType(String fuelType);
    List<Car> findByPriceLessThan(double price);
    List<Car> findFilteredCars(String brand, String model, Integer minYear, Integer maxYear, Integer minKw, Integer maxKw);
    List<String> getDistinctBrands();
    List<String> getDistinctModels();
    List<String> getDistinctModelsByBrand(String brand);
}