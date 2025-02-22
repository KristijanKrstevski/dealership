package com.example.dealership.web.controller;



import com.example.dealership.model.Car;
import com.example.dealership.model.Doors;
import com.example.dealership.model.Fuel;
import com.example.dealership.service.CarService;
import com.example.dealership.service.DoorService;
import com.example.dealership.service.FuelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;
    private final FuelService fuelService;
    private final DoorService doorService;

    public CarController(CarService carService, FuelService fuelService, DoorService doorService) {
        this.carService = carService;
        this.fuelService = fuelService;
        this.doorService = doorService;
    }

    @GetMapping("/delete/{id}")
    public String deleteCar(@PathVariable Long id) {
        carService.deleteById(id);
        return "redirect:/cars";
    }

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

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        List<Fuel> fuels = fuelService.findAllFuels();
        List<Doors> doors = doorService.findAllDoors();
        model.addAttribute("fuels", fuels);
        model.addAttribute("doorsList", doors);
        return "createCar";
    }

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

    // Keep all other existing methods the same below...
    @GetMapping("/checkCar/{id}")
    public String checkCar(@PathVariable Long id, Model model) {
        Car car = carService.findById(id).orElseThrow();
        model.addAttribute("car", car);
        return "checkCar";
    }
}

