package bg.tusofia.vvps.ticketsystem.route;

import bg.tusofia.vvps.ticketsystem.trainstation.TrainStation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RouteIntTest {


    @Autowired
    private RouteRepository routeRepository;

    @Test
    void test() {
        RouteService routeService = new RouteService(routeRepository);
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


}
