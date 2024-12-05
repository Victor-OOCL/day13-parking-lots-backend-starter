package org.afs.pakinglot.domain.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.afs.pakinglot.domain.Car;
import org.afs.pakinglot.domain.ParkingBoyType;
import org.afs.pakinglot.domain.ParkingLot;
import org.afs.pakinglot.domain.Ticket;
import org.afs.pakinglot.domain.controller.ParkingLotController;
import org.afs.pakinglot.domain.repository.ParkingLotRepository;
import org.afs.pakinglot.domain.response.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ParkingLotApiTest {

    private MockMvc mockMvc;

    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private ParkingLotController parkingLotController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(parkingLotController).build();
        parkingLotRepository = new ParkingLotRepository();
    }

    @Test
    public void should_returnAllParkingLots_when_getAllParkingLots_given_a_car() throws Exception {
        List<ParkingLot> parkingLots = parkingLotRepository.getAllParkingLots();
        parkingLots.get(0).park(new Car("ABC123"));

        mockMvc.perform(get("/parking-lots")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data[0].tickets[0].plateNumber").value("ABC123"))
                .andExpect(jsonPath("$.data[0].tickets[0].parkingLot").value(1));
    }

    @Test
    public void should_parkCarInFirstLot_when_park_givenSequentialParkingBoy() throws Exception {
        Car car = new Car("ABC123");

        mockMvc.perform(post("/parking-lots/park")
                        .param("parkingBoyType", ParkingBoyType.STANDARD)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"plateNumber\":\"ABC123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.plateNumber").value("ABC123"))
                .andExpect(jsonPath("$.data.position").value(1))
                .andExpect(jsonPath("$.data.parkingLot").value(1));
    }

    @Test
    public void should_parkCarInLotWithMaxAvailableSpaces_when_park_givenMaxAvailableSpacesParkingBoy() throws Exception {
        // Fill the parking lots with different numbers of cars
        ParkingLot lot1 = parkingLotRepository.getAllParkingLots().get(0);
        ParkingLot lot2 = parkingLotRepository.getAllParkingLots().get(1);
        ParkingLot lot3 = parkingLotRepository.getAllParkingLots().get(2);

        // Fill lot1 with 8 cars (1 space left)
        IntStream.range(0, 8).forEach(i -> lot1.park(new Car("CAR" + i)));

        // Fill lot2 with 5 cars (7 spaces left)
        IntStream.range(0, 5).forEach(i -> lot2.park(new Car("CAR" + (i + 8))));

        // Fill lot3 with 9 cars (0 spaces left)
        IntStream.range(0, 9).forEach(i -> lot3.park(new Car("CAR" + (i + 13))));

        Car car = new Car("DEF456");

        mockMvc.perform(post("/parking-lots/park")
                        .param("parkingBoyType", ParkingBoyType.SMART)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"plateNumber\":\"DEF456\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.plateNumber").value("DEF456"))// 6th position in lot2
                .andExpect(jsonPath("$.data.parkingLot").value(2)); // lot2 has the most spaces
    }

    @Test
    public void should_parkCarInLotWithMaxAvailablePositionRate_when_park_givenMaxAvailablePositionRateParkingBoy() throws Exception {
        // Fill the parking lots with different numbers of cars
        ParkingLot lot1 = parkingLotRepository.getAllParkingLots().get(0);
        ParkingLot lot2 = parkingLotRepository.getAllParkingLots().get(1);
        ParkingLot lot3 = parkingLotRepository.getAllParkingLots().get(2);

        // Fill lot1 with 8 cars (1 space left, 1/9 available rate)
        IntStream.range(0, 8).forEach(i -> lot1.park(new Car("CAR" + i)));

        // Fill lot2 with 10 cars (2 spaces left, 2/12 available rate)
        IntStream.range(0, 10).forEach(i -> lot2.park(new Car("CAR" + (i + 8))));

        // Fill lot3 with 7 cars (2 spaces left, 2/9 available rate)
        IntStream.range(0, 7).forEach(i -> lot3.park(new Car("CAR" + (i + 18))));

        Car car = new Car("GHI789");

        mockMvc.perform(post("/parking-lots/park")
                        .param("parkingBoyType", ParkingBoyType.SUPER_SMART)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"plateNumber\":\"GHI789\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.plateNumber").value("GHI789"))// 8th position in lot3
                .andExpect(jsonPath("$.data.parkingLot").value(3)); // lot3 has the highest available position rate
    }

    @Test
    public void should_throwException_when_park_givenInvalidParkingBoyType() throws Exception {
        Car car = new Car("XYZ123");

        mockMvc.perform(post("/parking-lots/park")
                        .param("parkingBoyType", "invalidType")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"plateNumber\":\"XYZ123\"}"))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Invalid parking boy type"));
    }

    @Test
    public void should_returnCar_when_fetch_givenValidTicketAndParkingBoyType() throws Exception {
        // Arrange
        Car car = new Car("ABC123");
        String parkingBoyType = ParkingBoyType.STANDARD;

        // Park the car and get the ticket
        MvcResult parkResult = mockMvc.perform(post("/parking-lots/park")
                        .param("parkingBoyType", parkingBoyType)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"plateNumber\":\"ABC123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.plateNumber").value("ABC123"))
                .andReturn();

        String resultJson = parkResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        ApiResponse<Ticket> apiResponse = objectMapper.readValue(resultJson, new TypeReference<ApiResponse<Ticket>>() {
        });

        // Act & Assert
        mockMvc.perform(post("/parking-lots/fetch")
                        .param("parkingBoyType", parkingBoyType)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(apiResponse.getData())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.plateNumber").value("ABC123"));
    }
}