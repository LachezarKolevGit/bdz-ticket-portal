package bg.tusofia.vvps.ticketsystem.train;

import bg.tusofia.vvps.ticketsystem.route.RouteService;
import bg.tusofia.vvps.ticketsystem.traincarriage.TrainCarriage;
import bg.tusofia.vvps.ticketsystem.traincarriage.TrainCarriageRepository;
import bg.tusofia.vvps.ticketsystem.traincarriage.TrainCarriageType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainServiceTest {
    public final static double DIESEL_PRICE = 2.80;

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
        Set<TrainCarriage> trainCarriageSet = Set.of(new TrainCarriage(TrainCarriageType.CLASS_A, 30),
                new TrainCarriage(TrainCarriageType.CLASS_B , 30),
                new TrainCarriage(TrainCarriageType.SLEEPER, 15));

        Train train = new Train(trainCarriageSet, null, null);
        Long trainId = 1L;
        when(trainRepository.findById(trainId)).thenReturn(Optional.of(train));
        when(routeService.calculateRouteDistance(train.getRoute())).thenReturn(361);

        double actualBasePrice = trainService.calculateBasePrice(trainId);
        int distance = 361;
        double expectedBasePrice = distance * DIESEL_PRICE / 75;
        assertEquals(expectedBasePrice, actualBasePrice, "ExpectedBasePrice does not match the actualBasePrice");
    }

}