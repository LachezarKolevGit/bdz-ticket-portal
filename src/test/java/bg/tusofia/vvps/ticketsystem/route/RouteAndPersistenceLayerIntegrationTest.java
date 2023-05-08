package bg.tusofia.vvps.ticketsystem.route;

import bg.tusofia.vvps.ticketsystem.train.Train;
import bg.tusofia.vvps.ticketsystem.traincarriage.TrainCarriage;
import bg.tusofia.vvps.ticketsystem.traincarriage.TrainCarriageType;
import bg.tusofia.vvps.ticketsystem.trainstation.TrainStation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RouteAndPersistenceLayerIntegrationTest {

    @Autowired
    private RouteRepository routeRepository;

    RouteService routeService;

    @BeforeEach
    void init() {
        this.routeService = new RouteService(routeRepository);
    }

    @Test
    void testRouteServiceGetRoutes() {
        TrainStation sofiaTrainStation = new TrainStation("Sofia Central station", 42.712115, 23.321046);
        TrainStation plovdivTrainStation = new TrainStation("Plovdiv Central railway station", 42.134444, 24.741389);
        TrainStation burgasTrainStation = new TrainStation("Burgas Central railway station", 42.490833, 27.4725);

        List<TrainStation> trainStationList = List.of(sofiaTrainStation, plovdivTrainStation, burgasTrainStation);

        Route route = new Route(trainStationList, null);
        Route route1 = new Route(trainStationList, null);
        Route route2 = new Route(trainStationList, null);
        Route route3 = new Route(trainStationList, null);

        List<Route> expectedRouteList = List.of(route, route1, route2, route3);
        routeRepository.saveAll(expectedRouteList);
        Page<Route> routesPage = routeService.getRoutes(0);
        List<Route> actualRoutesList = routesPage.getContent();
        assertEquals(expectedRouteList, actualRoutesList, "Expected and actual routes saved in the db does not match");
    }

    @Test
    void testRouteServiceGetRoute() {
        TrainStation sofiaTrainStation = new TrainStation("Sofia Central station", 42.712115, 23.321046);
        TrainStation plovdivTrainStation = new TrainStation("Plovdiv Central railway station", 42.134444, 24.741389);
        TrainStation burgasTrainStation = new TrainStation("Burgas Central railway station", 42.490833, 27.4725);

        List<TrainStation> trainStationList = List.of(sofiaTrainStation, plovdivTrainStation, burgasTrainStation);

        Route expectedRoute = new Route(trainStationList, null);

        routeRepository.save(expectedRoute);
        Route actualRoute = routeService.getRoute(expectedRoute.getId());
        assertEquals(expectedRoute, actualRoute, "Expected and actual saved route in the db does not match");
    }

    @DisplayName("Test for correctly saved routes in the database")
    @Test
    void testCreateRoute() {
        TrainStation sofiaTrainStation = new TrainStation("Sofia Central station", 42.712115, 23.321046);
        TrainStation plovdivTrainStation = new TrainStation("Plovdiv Central railway station", 42.134444, 24.741389);
        TrainStation burgasTrainStation = new TrainStation("Burgas Central railway station", 42.490833, 27.4725);

        List<TrainStation> trainStationList = List.of(sofiaTrainStation, plovdivTrainStation, burgasTrainStation);
        TrainCarriage trainCarriage1 = new TrainCarriage(TrainCarriageType.CLASS_A, 30);
        TrainCarriage trainCarriage2 = new TrainCarriage(TrainCarriageType.CLASS_A, 30);
        TrainCarriage trainCarriage3 = new TrainCarriage(TrainCarriageType.CLASS_A, 30);

        Train train = new Train(Set.of(trainCarriage1, trainCarriage2, trainCarriage3),
                LocalDateTime.of(2022, 5, 1, 20, 30),
                LocalDateTime.of(2022, 5, 2, 6, 30));

        RouteDTO routeDTO = new RouteDTO(trainStationList, Set.of(train));
        Long routeId = routeService.createNewRoute(routeDTO);
        System.out.println("rotue id " + routeId);

        Route actualRoute = routeService.getRoute(routeId);
        assertEquals(Set.of(train), actualRoute.getTrains(),
                "ExpectedRoute that is saved in the db is not the same as the actualRoute returned by the db");

    }
}
