package org.afs.pakinglot.domain.controller;

import org.afs.pakinglot.domain.Car;
import org.afs.pakinglot.domain.ParkingLot;
import org.afs.pakinglot.domain.Ticket;
import org.afs.pakinglot.domain.response.ApiResponse;
import org.afs.pakinglot.domain.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/parking-lots")
public class ParkingLotController {
    private final ParkingLotService parkingLotService;

    @Autowired
    public ParkingLotController(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }

    @GetMapping
    public ApiResponse<List<ParkingLot>> getAllParkingLots() {
        return parkingLotService.getAllParkingLots();
    }

    @PostMapping("/park")
    public ApiResponse<Ticket> park(@RequestParam String parkingBoyType, @RequestBody Car car) {
        return parkingLotService.park(parkingBoyType, car);
    }

    @PostMapping("/fetch")
    public ApiResponse<Car> fetch(@RequestParam String parkingBoyType, @RequestBody Ticket ticket) {
        return parkingLotService.fetch(parkingBoyType, ticket);
    }
}
