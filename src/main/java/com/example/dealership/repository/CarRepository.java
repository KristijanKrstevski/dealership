package com.example.dealership.repository;



import com.example.dealership.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByBrand(String brand);
    List<Car> findByFuel_FuelType(String fuelType);
    List<Car> findByPriceLessThan(double price);

    @Query("SELECT DISTINCT c.brand FROM Car c")
    List<String> findDistinctBrands();

    @Query("SELECT DISTINCT c.model FROM Car c")
    List<String> findDistinctModels();

    @Query("SELECT DISTINCT c.model FROM Car c WHERE c.brand = :brand")
    List<String> findDistinctModelsByBrand(@Param("brand") String brand);

    @Query("SELECT c FROM Car c WHERE " +
            "(COALESCE(:brand, '') = '' OR c.brand = :brand) AND " +
            "(COALESCE(:model, '') = '' OR c.model = :model) AND " +
            "(:minYear IS NULL OR c.year >= :minYear) AND " +
            "(:maxYear IS NULL OR c.year <= :maxYear) AND " +
            "(:minKw IS NULL OR c.kw >= :minKw) AND " +
            "(:maxKw IS NULL OR c.kw <= :maxKw)")
    List<Car> findFilteredCars(
            @Param("brand") String brand,
            @Param("model") String model,
            @Param("minYear") Integer minYear,
            @Param("maxYear") Integer maxYear,
            @Param("minKw") Integer minKw,
            @Param("maxKw") Integer maxKw
    );
}