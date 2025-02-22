package com.example.dealership.service.impl;

import com.example.dealership.model.CarPhoto;
import com.example.dealership.repository.CarPhotoRepository;
import com.example.dealership.service.CarPhotoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarPhotoServiceImpl implements CarPhotoService {

    private final CarPhotoRepository carPhotoRepository;

    public CarPhotoServiceImpl(CarPhotoRepository carPhotoRepository) {
        this.carPhotoRepository = carPhotoRepository;
    }

    @Override
    public List<CarPhoto> findByCarId(Long carId) {
        return carPhotoRepository.findByCarId(carId);
    }

    @Override
    public CarPhoto save(CarPhoto carPhoto) {
        return carPhotoRepository.save(carPhoto);
    }
}

