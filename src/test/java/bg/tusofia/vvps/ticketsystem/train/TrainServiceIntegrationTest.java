package bg.tusofia.vvps.ticketsystem.train;

import bg.tusofia.vvps.ticketsystem.route.Route;
import bg.tusofia.vvps.ticketsystem.route.RouteRepository;
import bg.tusofia.vvps.ticketsystem.traincarriage.TrainCarriage;
import bg.tusofia.vvps.ticketsystem.traincarriage.TrainCarriageType;
import bg.tusofia.vvps.ticketsystem.trainstation.TrainStation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TrainServiceIntegrationTest {
    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Test
    void test() {
        TrainStation sofiaTrainStation = new TrainStation("Sofia Central station", 42.712115, 23.321046);
        TrainStation plovdivTrainStation = new TrainStation("Plovdiv Central railway station", 42.134444, 24.741389);
        TrainStation burgasTrainStation = new TrainStation("Burgas Central railway station", 42.490833, 27.4725);
        List<TrainStation> trainStationList = List.of(sofiaTrainStation, plovdivTrainStation, burgasTrainStation);

        TrainStation burgasTrainStation1 = new TrainStation("Burgas Central railway station", 42.490833, 27.4725);
        TrainStation plovdivTrainStation1 = new TrainStation("Plovdiv Central railway station", 42.134444, 24.741389);
        TrainStation sofiaTrainStation1 = new TrainStation("Sofia Central station", 42.712115, 23.321046);
        List<TrainStation> trainStationList1 = List.of(burgasTrainStation, plovdivTrainStation, sofiaTrainStation);

        TrainCarriage trainCarriage1 = new TrainCarriage(TrainCarriageType.CLASS_A, 30);
        TrainCarriage trainCarriage2 = new TrainCarriage(TrainCarriageType.CLASS_A, 30);
        TrainCarriage trainCarriage3 = new TrainCarriage(TrainCarriageType.CLASS_A, 30);
        Set<TrainCarriage> trainCarriageSet = Set.of(trainCarriage1, trainCarriage2, trainCarriage3);

        Train train = new Train(trainCarriageSet,
                LocalDateTime.of(2022, 5, 1, 20, 30),
                LocalDateTime.of(2022, 5, 2, 6, 30));

        Train train1 = new Train(trainCarriageSet,
                LocalDateTime.of(2022, 5, 1, 20, 30),
                LocalDateTime.of(2022, 5, 2, 6, 30));

        Route route = new Route(trainStationList, null);
        routeRepository.save(route);
        train.assignRoute(route);

        Route route1 = new Route(trainStationList1, null);
        routeRepository.save(route1);
        train1.assignRoute(route1);

        trainRepository.save(train);
        trainRepository.save(train1);

        List<Train> expectedTrainList = new ArrayList<>();
        expectedTrainList.add(train);

        List<Train> actualTrainList = trainRepository
                .getTrainsByDestinationAndDateTime("Burgas Central railway station",
                        LocalDateTime.of(2022, 5, 1, 20, 30));

        System.out.println(LocalDateTime.of(2022, 5, 1, 20, 30));

        assertEquals(expectedTrainList, actualTrainList,
                "The expected and actual train list returned by search with arrival" +
                        " train stations and departure time does not match");
    }

}
