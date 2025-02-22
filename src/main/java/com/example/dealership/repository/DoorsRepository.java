package com.example.dealership.repository;


import com.example.dealership.model.Doors;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoorsRepository extends JpaRepository<Doors, Long> {
}