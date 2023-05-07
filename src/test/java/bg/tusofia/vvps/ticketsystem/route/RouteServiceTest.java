package bg.tusofia.vvps.ticketsystem.route;

import bg.tusofia.vvps.ticketsystem.trainstation.TrainStation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RouteServiceTest {

    static RouteService routeService;

    @BeforeAll
    static void init() {
        routeService = new RouteService(null);
    }

    @DisplayName("Test for correct value returned by .calculateDistanceInKilometer() for distance in km between two points")
    @Test
    void testCalculateDistanceInKilometer() {
        TrainStation sofiaTrainStation = new TrainStation("Sofia Central station", 42.712115, 23.321046);
        TrainStation plovdivTrainStation = new TrainStation("Plovdiv Central railway station", 42.134444, 24.741389);

        int actualDistance = routeService.calculateDistanceInKilometer(sofiaTrainStation.getLatitude(), sofiaTrainStation.getLongitude()
                , plovdivTrainStation.getLatitude(), plovdivTrainStation.getLongitude());
        int expectedDistance = 133;
        assertEquals(expectedDistance, actualDistance, "The expectedDistance does not match the actualDistance");
    }

    @DisplayName("Test for correct returned value of .calculateRouteDistance() method")
    @Test
    void testCalculateRouteDistance() {
        TrainStation sofiaTrainStation = new TrainStation("Sofia Central station", 42.712115, 23.321046);
        TrainStation plovdivTrainStation = new TrainStation("Plovdiv Central railway station", 42.134444, 24.741389);
        TrainStation burgasTrainStation = new TrainStation("Burgas Central railway station", 42.490833, 27.4725);

        List<TrainStation> trainStationList = List.of(sofiaTrainStation, plovdivTrainStation, burgasTrainStation);

        Route route = new Route(trainStationList, null);
        int actualRouteDistance = routeService.calculateRouteDistance(route);
        double expectedRouteDistance = 361;
        assertEquals(expectedRouteDistance, actualRouteDistance, "ExpectedRouteDistance is not the same as actualRouteDistance");
    }

}