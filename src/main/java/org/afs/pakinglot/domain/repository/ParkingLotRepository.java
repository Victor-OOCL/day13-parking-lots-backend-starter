package org.afs.pakinglot.domain.repository;

import org.afs.pakinglot.domain.Car;
import org.afs.pakinglot.domain.ParkingBoy;
import org.afs.pakinglot.domain.ParkingBoyType;
import org.afs.pakinglot.domain.ParkingLot;
import org.afs.pakinglot.domain.strategies.AvailableRateStrategy;
import org.afs.pakinglot.domain.strategies.MaxAvailableStrategy;
import org.afs.pakinglot.domain.strategies.SequentiallyStrategy;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ParkingLotRepository {
    private final List<ParkingLot> parkingLots;
    private final Map<ParkingBoyType, ParkingBoy> parkingBoys;

    public ParkingLotRepository() {
        parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot(1, "The Plaza Park", 9));
        parkingLots.add(new ParkingLot(2, "City Mall Garage", 12));
        parkingLots.add(new ParkingLot(3, "Office Tower Parking", 9));

        parkingBoys = new HashMap<>();
        parkingBoys.put(ParkingBoyType.STANDARD, new ParkingBoy(parkingLots,new SequentiallyStrategy()));
        parkingBoys.put(ParkingBoyType.SMART, new ParkingBoy(parkingLots,new MaxAvailableStrategy()));
        parkingBoys.put(ParkingBoyType.SUPER_SMART, new ParkingBoy(parkingLots,new AvailableRateStrategy()));
    }

    public List<ParkingLot> getAllParkingLots() {
        return parkingLots;
    }
}
