package com.example.RentalCar.service;

// Tu dois IMPORTER Car et Dates depuis le package entity !
import com.example.RentalCar.entity.Car;
import com.example.RentalCar.entity.Dates;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CarRentalController {

    private List<Car> cars = new ArrayList<>();

    public CarRentalController() {
        // Liste de voitures par défaut
        cars.add(new Car("11AA22", "Ferrari", 100));
        cars.add(new Car("33BB44", "Porsche", 150));
    }

    // GET /cars : Obtenir toutes les voitures
    @GetMapping("/cars")
    @ResponseStatus(HttpStatus.OK)
    public List<Car> listOfCars() {
        return cars;
    }

    // GET /cars/{plateNumber} : Obtenir une voiture spécifique
    @GetMapping("/cars/{plateNumber}")
    @ResponseStatus(HttpStatus.OK)
    public Car aCar(@PathVariable("plateNumber") String plateNumber) throws Exception {
        return cars.stream()
                .filter(car -> car.getPlateNumber().equalsIgnoreCase(plateNumber))
                .findFirst()
                .orElseThrow(() -> new Exception("Voiture introuvable"));
    }

    // PUT /cars/{plateNumber}?rent=true ou false
    @PutMapping("/cars/{plateNumber}")
    @ResponseStatus(HttpStatus.OK)
    public void rentOrGetBack(
            @PathVariable("plateNumber") String plateNumber,
            @RequestParam(value = "rent", required = true) boolean rent,
            @RequestBody(required = false) Dates dates) throws Exception {
        
        Car car = aCar(plateNumber);
        car.setRented(rent);
    }
}