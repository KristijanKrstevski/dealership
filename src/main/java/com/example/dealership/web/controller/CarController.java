package com.example.dealership.web.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;  // Correct Path import for file handling
import java.nio.file.Paths;
import com.example.dealership.model.Car;
import com.example.dealership.model.CarPhoto;
import com.example.dealership.model.Doors;
import com.example.dealership.model.Fuel;
import com.example.dealership.service.CarPhotoService;
import com.example.dealership.service.CarService;
import com.example.dealership.service.DoorService;
import com.example.dealership.service.FuelService;
//import jakarta.persistence.criteria.Path;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;
    private final FuelService fuelService;
    private final DoorService doorService;
    private final CarPhotoService carPhotoService;

    public CarController(CarService carService, FuelService fuelService, DoorService doorService, CarPhotoService carPhotoService) {
        this.carService = carService;
        this.fuelService = fuelService;
        this.doorService = doorService;
        this.carPhotoService = carPhotoService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteCar(@PathVariable Long id) {
        carService.deleteById(id);
        return "redirect:/cars";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/editCar/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Car car = carService.findById(id).orElseThrow();
        List<Fuel> fuels = fuelService.findAllFuels();
        List<Doors> doors = doorService.findAllDoors();
        model.addAttribute("car", car);
        model.addAttribute("fuels", fuels);
        model.addAttribute("doorsList", doors);
        return "editCar";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/edit/{id}")
    public String updateCar(@PathVariable Long id,
                            @ModelAttribute Car car,
                            @RequestParam Long fuelId,
                            @RequestParam Long doorsId) {
        Fuel fuel = fuelService.findAllFuels().stream()
                .filter(f -> f.getId().equals(fuelId))
                .findFirst()
                .orElseThrow();
        Doors doors = doorService.findAllDoors().stream()
                .filter(d -> d.getId().equals(doorsId))
                .findFirst()
                .orElseThrow();

        car.setFuel(fuel);
        car.setDoors(doors);
        carService.update(car);
        return "redirect:/cars";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        List<Fuel> fuels = fuelService.findAllFuels();
        List<Doors> doors = doorService.findAllDoors();
        model.addAttribute("fuels", fuels);
        model.addAttribute("doorsList", doors);
        return "createCar";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public String createCar(@RequestParam String brand,
                            @RequestParam String model,
                            @RequestParam int kw,
                            @RequestParam int year,
                            @RequestParam Long fuelId,
                            @RequestParam String color,
                            @RequestParam Long doorsId,
                            @RequestParam int km,
                            @RequestParam double price) {
        Fuel fuel = fuelService.findAllFuels().stream()
                .filter(f -> f.getId().equals(fuelId))
                .findFirst()
                .orElseThrow();
        Doors doors = doorService.findAllDoors().stream()
                .filter(d -> d.getId().equals(doorsId))
                .findFirst()
                .orElseThrow();

        Car car = new Car(brand, model, kw, year, fuel, color, doors, km, price);
        carService.save(car);
        return "redirect:/cars";
    }
    @GetMapping
    public String getCarsPage(
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) Integer minYear,
            @RequestParam(required = false) Integer maxYear,
            @RequestParam(required = false) Integer minKw,
            @RequestParam(required = false) Integer maxKw,
            Model modelView) {

        // Validate year range
        if (minYear != null && maxYear != null && minYear > maxYear) {
            int temp = minYear;
            minYear = maxYear;
            maxYear = temp;
        }

        // Validate KW range
        if (minKw != null && maxKw != null && minKw > maxKw) {
            int temp = minKw;
            minKw = maxKw;
            maxKw = temp;
        }

        List<Car> cars = carService.findFilteredCars(
                brand, model, minYear, maxYear, minKw, maxKw
        );

        List<String> brands = carService.getDistinctBrands();
        List<String> models = brand != null && !brand.isEmpty() ?
                carService.getDistinctModelsByBrand(brand) :
                carService.getDistinctModels();

        modelView.addAttribute("cars", cars);
        modelView.addAttribute("brands", brands);
        modelView.addAttribute("models", models);
        modelView.addAttribute("selectedBrand", brand);
        modelView.addAttribute("selectedModel", model);
        modelView.addAttribute("minYear", minYear);
        modelView.addAttribute("maxYear", maxYear);
        modelView.addAttribute("minKw", minKw);
        modelView.addAttribute("maxKw", maxKw);

        return "cars";
    }



    @GetMapping("/checkCar/{id}")
    public String checkCar(@PathVariable Long id, Model model) {
        Car car = carService.findById(id).orElseThrow();
        model.addAttribute("car", car);

        // Get photos of the car
        List<CarPhoto> carPhotos = carPhotoService.findByCarId(id);
        model.addAttribute("photos", carPhotos);

        return "checkCar";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/uploadPhoto/{carId}")
    public String uploadCarPhoto(@PathVariable Long carId, @RequestParam("file") MultipartFile file) throws IOException {

        String folderPath = "storage/cars/" + carId;
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(folderPath, fileName);
        File fileToSave = filePath.toFile();
        file.transferTo(fileToSave);


        Car car = carService.findById(carId).orElseThrow();
        CarPhoto carPhoto = new CarPhoto();
        carPhoto.setCar(car);
        carPhoto.setPhotoUrl(filePath.toString());
        carPhotoService.save(carPhoto);

        return "redirect:/cars/checkCar/" + carId;
    }

}

