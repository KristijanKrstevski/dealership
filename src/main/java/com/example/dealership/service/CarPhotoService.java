package com.example.dealership.service;

import com.example.dealership.model.CarPhoto;

import java.util.List;

public interface CarPhotoService {
    List<CarPhoto> findByCarId(Long carId);
    CarPhoto save(CarPhoto carPhoto);
}