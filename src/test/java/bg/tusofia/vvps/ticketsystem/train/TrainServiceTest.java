package bg.tusofia.vvps.ticketsystem.train;

import bg.tusofia.vvps.ticketsystem.route.Route;
import bg.tusofia.vvps.ticketsystem.traincarriage.TrainCarriage;
import bg.tusofia.vvps.ticketsystem.traincarriage.TrainCarriageRepository;
import bg.tusofia.vvps.ticketsystem.traincarriage.TrainCarriageType;
import bg.tusofia.vvps.ticketsystem.traincarriage.seat.SeatRepository;
import bg.tusofia.vvps.ticketsystem.trainstation.TrainStation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class TrainServiceTest {

    private static double DIESEL_PRICE = 2.80;

    static TrainService trainService;

    @Mock
    static RouteService routeService;

    @Mock
    static TrainRepository trainRepository;

    @Mock
    static TrainCarriageRepository trainCarriageRepository;

    @Mock
     static SeatRepository seatRepository;

    @BeforeAll
    static void init() {
        trainService = new TrainService(trainRepository, routeService, trainCarriageRepository, seatRepository );
    }

    @DisplayName("Test .calculateBasePrice() method if it returns the correct value")
    @Test
    void testCalculateBasePrice() {
        *//
        30 + 30 + 15 = 75 total seats
        //*

        TrainStation sofiaTrainStation = new TrainStation("Sofia Central station", 42.712115, 23.321046);
        TrainStation plovdivTrainStation = new TrainStation("Plovdiv Central railway station", 42.134444, 24.741389);
        TrainStation burgasTrainStation = new TrainStation("Burgas Central railway station", 42.490833, 27.4725);

        List<TrainStation> trainStationList = List.of(sofiaTrainStation, plovdivTrainStation, burgasTrainStation);

        Route route = new Route(trainStationList, null);
        Set<TrainCarriage> trainCarriageSet = Set.of(new TrainCarriage(TrainCarriageType.CLASS_A, null, 30),
                new TrainCarriage(TrainCarriageType.CLASS_B, null, 30),
                new TrainCarriage(TrainCarriageType.SLEEPER, null, 15));

        /*new HashSet<>();
        TrainCarriage trainCarriage1 = new TrainCarriage(TrainCarriageType.CLASS_A, null, 30);
        TrainCarriage trainCarriage2 = new TrainCarriage(TrainCarriageType.CLASS_B, null, 30);
        TrainCarriage trainCarriage3 = new TrainCarriage(TrainCarriageType.SLEEPER, null, 15);*/

        Train train = new Train(trainCarriageSet, null, null, route);
        Long trainId = 1L;
        when(trainRepository.findById(trainId)).thenReturn(Optional.of(train));
        when(routeService.calculateRouteDistance(route)).thenReturn(); //later addd the total distance of the route
        
        int distance = routeService.calculateRouteDistance(route);

        double actualBasePrice = trainService.calculateBasePrice(trainId);
        double expectedBasePrice = distance * DIESEL_PRICE / 75;
        assertEquals(expectedBasePrice, actualBasePrice, "ExpectedBasePrice does not match the actualBasePrice");
    }

    @DisplayName("Test .getTrainCarriage() method if it returns the correct value")
    @Test
    void testGetTrainCarriageClass(){
        TrainCarriage trainCarriage = new TrainCarriage(TrainCarriageType.CLASS_B,null, 30);
        Long trainCarriageId = 1L;
        when(trainCarriageRepository.findById(trainCarriageId)).thenReturn(Optional.of(trainCarriage));
        double actualMultiplier = trainService.getTrainCarriageClass(trainCarriage.getId(trainCarriageId));
        double expectedMultiplier = 1;
        
        assertEquals(expectedMuilplier, actualMuilplier, "ExpectedMultiplier is not the same as actualMultiplier");
        
    }

    @DisplayName("Test .changeSeatStatus() method")
    @Test
    void testChangeSeatStatus(){
        Long seatId = 1L;
        trainService.changeSeatStatus(seatId);
       Seat seat = seatRepository.findById(seatId);
       SeatState actualSeatState = seat.getSeatState();
       SeatState expectedSeatState = SeatState.SOLD;

       assertEquals(expectedSeatState, actualSeatState, "ExpectedSeatState does not match the actualSeatState");


    }


}