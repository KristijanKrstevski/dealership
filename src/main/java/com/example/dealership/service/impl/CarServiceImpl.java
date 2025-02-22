package com.example.dealership.service.impl;


import com.example.dealership.model.Car;
import com.example.dealership.repository.CarRepository;
import com.example.dealership.service.CarService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override public List<Car> findAll() { return carRepository.findAll(); }
    @Override public Optional<Car> findById(Long id) { return carRepository.findById(id); }
    @Override public Car save(Car car) { return carRepository.save(car); }
    @Override public void deleteById(Long id) { carRepository.deleteById(id); }
    @Override public Car update(Car car) { return carRepository.save(car); }
    @Override public List<Car> findByBrand(String brand) { return carRepository.findByBrand(brand); }
    @Override public List<Car> findByFuelType(String fuelType) { return carRepository.findByFuel_FuelType(fuelType); }
    @Override public List<Car> findByPriceLessThan(double price) { return carRepository.findByPriceLessThan(price); }

    @Override
    public List<Car> findFilteredCars(String brand, String model, Integer minYear,
                                      Integer maxYear, Integer minKw, Integer maxKw) {
        return carRepository.findFilteredCars(brand, model, minYear, maxYear, minKw, maxKw);
    }

    @Override
    public List<String> getDistinctBrands() {
        return carRepository.findDistinctBrands();
    }

    @Override
    public List<String> getDistinctModels() {
        return carRepository.findDistinctModels();
    }

    @Override
    public List<String> getDistinctModelsByBrand(String brand) {
        return carRepository.findDistinctModelsByBrand(brand);
    }
}