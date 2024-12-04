package org.afs.pakinglot.domain.api;

import org.afs.pakinglot.domain.Car;
import org.afs.pakinglot.domain.ParkingLot;
import org.afs.pakinglot.domain.controller.ParkingLotController;
import org.afs.pakinglot.domain.repository.ParkingLotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
public class ParkingLotApiTest {

    private MockMvc mockMvc;

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private ParkingLotController parkingLotController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(parkingLotController).build();
    }

    @Test
    public void should_returnAllParkingLots_when_getAllParkingLots() throws Exception {
        List<ParkingLot> parkingLots = parkingLotRepository.getAllParkingLots();
        for (ParkingLot parkingLot : parkingLots) {
            parkingLot.park(new Car("ABC123"));
        }
        mockMvc.perform(get("/parking-lot")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data[0].tickets[0].plateNumber").value("ABC123"))
                .andExpect(jsonPath("$.data[0].tickets[0].position").value(1))
                .andExpect(jsonPath("$.data[0].tickets[0].parkingLot").value(1))
                .andExpect(jsonPath("$.data[1].tickets[0].plateNumber").value("ABC123"))
                .andExpect(jsonPath("$.data[1].tickets[0].position").value(1))
                .andExpect(jsonPath("$.data[1].tickets[0].parkingLot").value(2))
                .andExpect(jsonPath("$.data[2].tickets[0].plateNumber").value("ABC123"))
                .andExpect(jsonPath("$.data[2].tickets[0].position").value(1))
                .andExpect(jsonPath("$.data[2].tickets[0].parkingLot").value(3));
    }
}
