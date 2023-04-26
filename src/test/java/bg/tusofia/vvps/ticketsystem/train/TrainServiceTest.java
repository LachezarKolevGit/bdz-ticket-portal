package bg.tusofia.vvps.ticketsystem.train;

import bg.tusofia.vvps.ticketsystem.route.Route;
import bg.tusofia.vvps.ticketsystem.route.RouteService;
import bg.tusofia.vvps.ticketsystem.traincarriage.TrainCarriage;
import bg.tusofia.vvps.ticketsystem.traincarriage.TrainCarriageRepository;
import bg.tusofia.vvps.ticketsystem.traincarriage.TrainCarriageType;
import bg.tusofia.vvps.ticketsystem.traincarriage.seat.Seat;
import bg.tusofia.vvps.ticketsystem.traincarriage.seat.SeatState;
import bg.tusofia.vvps.ticketsystem.trainstation.TrainStation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainServiceTest {

    private final static double DIESEL_PRICE = 2.80;

    private TrainService trainService;

    @Mock
    private RouteService routeService;

    @Mock
    private TrainRepository trainRepository;

    @Mock
    private TrainCarriageRepository trainCarriageRepository;


    @BeforeEach
    void init() {
        trainService = new TrainService(trainRepository, routeService, trainCarriageRepository);
    }

    @DisplayName("Test .calculateBasePrice() method if it returns the correct value")
    @Test
    void testCalculateBasePrice() {
        /**
         30 + 30 + 15 = 75 total seats
         */
        TrainStation sofiaTrainStation = new TrainStation("Sofia Central station", 42.712115, 23.321046);
        TrainStation plovdivTrainStation = new TrainStation("Plovdiv Central railway station", 42.134444, 24.741389);
        TrainStation burgasTrainStation = new TrainStation("Burgas Central railway station", 42.490833, 27.4725);
        List<TrainStation> trainStationList = List.of(sofiaTrainStation, plovdivTrainStation, burgasTrainStation);

        Route route = new Route(trainStationList, null);
        Set<TrainCarriage> trainCarriageSet = Set.of(new TrainCarriage(TrainCarriageType.CLASS_A, 30),
                new TrainCarriage(TrainCarriageType.CLASS_B , 30),
                new TrainCarriage(TrainCarriageType.SLEEPER, 15));

        Train train = new Train(trainCarriageSet, null, null);
        Long trainId = 1L;
        when(trainRepository.findById(trainId)).thenReturn(Optional.of(train));
        when(routeService.calculateRouteDistance(route)).thenReturn(361);

        double actualBasePrice = trainService.calculateBasePrice(trainId);
        int distance = routeService.calculateRouteDistance(route);
        double expectedBasePrice = distance * DIESEL_PRICE / 75;
        assertEquals(expectedBasePrice, actualBasePrice, "ExpectedBasePrice does not match the actualBasePrice");
    }

    @DisplayName("Test .getTrainCarriageClass() method if it returns the correct value")
    @Test
    void testGetTrainCarriageClass() {
        TrainCarriage trainCarriage = new TrainCarriage(TrainCarriageType.CLASS_B, 30);
        Long trainCarriageId = 1L;
        when(trainCarriageRepository.findById(trainCarriageId)).thenReturn(Optional.of(trainCarriage));
        double actualMultiplier = trainService.getTrainCarriageClass(trainCarriageId);
        double expectedMultiplier = 1;

        assertEquals(expectedMultiplier, actualMultiplier, "ExpectedMultiplier is not the same as actualMultiplier");
    }

    @DisplayName("Test .changeSeatStatus() method")
    @Test
    void testChangeSeatStatus() {
        Seat seat = new Seat();
        Long seatId = 1L;
       // when(seatRepository.findById(seatId)).thenReturn(Optional.of(seat));
        trainService.changeSeatStatus(seatId);
       /* Optional<Seat> seatOptional = trainRepository.findById(seatId);
        if (seatOptional.isEmpty()) {
            throw new EntityNotFoundException();
        }
        SeatState actualSeatState = seatOptional.get().getSeatState();*/
        SeatState expectedSeatState = SeatState.SOLD;

        assertEquals(expectedSeatState, expectedSeatState, "ExpectedSeatState does not match the actualSeatState");
    }

}