package org.afs.pakinglot.domain.repository;

import org.afs.pakinglot.domain.*;
import org.afs.pakinglot.domain.strategies.AvailableRateStrategy;
import org.afs.pakinglot.domain.strategies.MaxAvailableStrategy;
import org.afs.pakinglot.domain.strategies.SequentiallyStrategy;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ParkingLotRepository {
    private final List<ParkingLot> parkingLots;
    private final Map<String, ParkingBoy> parkingBoys;

    public ParkingLotRepository() {
        parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot(1, "The Plaza Park", 9));
        parkingLots.add(new ParkingLot(2, "City Mall Garage", 12));
        parkingLots.add(new ParkingLot(3, "Office Tower Parking", 9));

        parkingBoys = new HashMap<>();
        parkingBoys.put(ParkingBoyType.STANDARD, new ParkingBoy(parkingLots, new SequentiallyStrategy()));
        parkingBoys.put(ParkingBoyType.SMART, new ParkingBoy(parkingLots, new MaxAvailableStrategy()));
        parkingBoys.put(ParkingBoyType.SUPER_SMART, new ParkingBoy(parkingLots, new AvailableRateStrategy()));
    }

    public List<ParkingLot> getAllParkingLots() {
        return parkingLots;
    }

    public Ticket park(String parkingBoyType, Car car) {
        ParkingBoy parkingBoy = parkingBoys.get(parkingBoyType);
        if (parkingBoy == null) {
            return null;
        }
        return parkingBoy.park(car);
    }

    public Car fetch(String parkingBoyType, Ticket ticket) {
        ParkingBoy parkingBoy = parkingBoys.get(parkingBoyType);
        if (parkingBoy == null) {
            return null;
        }
        return parkingBoy.fetch(ticket);
    }
}
