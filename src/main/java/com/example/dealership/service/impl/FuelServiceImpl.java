package com.example.dealership.service.impl;


import com.example.dealership.model.Fuel;
import com.example.dealership.repository.FuelRepository;
import com.example.dealership.service.FuelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuelServiceImpl implements FuelService {
    private final FuelRepository fuelRepository;

    public FuelServiceImpl(FuelRepository fuelRepository) {
        this.fuelRepository = fuelRepository;
    }

    @Override
    public List<Fuel> findAllFuels() {
        return fuelRepository.findAll();
    }
}