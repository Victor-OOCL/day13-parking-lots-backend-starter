package org.afs.pakinglot.domain.controller;

import org.afs.pakinglot.domain.ParkingLot;
import org.afs.pakinglot.domain.response.ApiResponse;
import org.afs.pakinglot.domain.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/parking-lot")
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
}
