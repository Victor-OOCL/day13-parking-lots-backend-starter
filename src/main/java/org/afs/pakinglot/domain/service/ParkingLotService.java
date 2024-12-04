package org.afs.pakinglot.domain.service;

import org.afs.pakinglot.domain.ParkingLot;
import org.afs.pakinglot.domain.repository.ParkingLotRepository;
import org.afs.pakinglot.domain.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingLotService {

    private final ParkingLotRepository parkingLotRepository;

    @Autowired
    public ParkingLotService(ParkingLotRepository parkingLotRepository) {
        this.parkingLotRepository = parkingLotRepository;
    }

    public ApiResponse<List<ParkingLot>> getAllParkingLots() {
        List<ParkingLot> parkingLots = parkingLotRepository.getAllParkingLots();
        return ApiResponse.success(parkingLots);
    }
}
