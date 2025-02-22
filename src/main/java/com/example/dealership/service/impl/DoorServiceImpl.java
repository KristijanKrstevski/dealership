package com.example.dealership.service.impl;


import com.example.dealership.model.Doors;
import com.example.dealership.repository.DoorsRepository;
import com.example.dealership.service.DoorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoorServiceImpl implements DoorService {
    private final DoorsRepository doorsRepository;

    public DoorServiceImpl(DoorsRepository doorsRepository) {
        this.doorsRepository = doorsRepository;
    }

    @Override
    public List<Doors> findAllDoors() {
        return doorsRepository.findAll();
    }
}