package com.example.dealership.service;


import com.example.dealership.model.Doors;

import java.util.List;

public interface DoorService {
    List<Doors> findAllDoors();
}