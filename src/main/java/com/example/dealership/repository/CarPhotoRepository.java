package com.example.dealership.repository;

import com.example.dealership.model.CarPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarPhotoRepository extends JpaRepository<CarPhoto, Long> {
    List<CarPhoto> findByCarId(Long carId);
}
