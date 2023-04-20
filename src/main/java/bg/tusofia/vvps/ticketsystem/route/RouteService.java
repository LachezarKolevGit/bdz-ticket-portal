package bg.tusofia.vvps.ticketsystem.route;

import bg.tusofia.vvps.ticketsystem.trainstation.TrainStation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {
    public final static double AVERAGE_RADIUS_OF_EARTH_KM = 6371;

    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public Page<Route> getRoutes(int page) {
        int pageSize = 10;
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return routeRepository.findAll(pageRequest);
    }

    public Long saveRoute(RouteDTO routeDTO) {
        Route route = new Route(routeDTO.stops(), routeDTO.trains());
        routeRepository.save(route);
        return route.getId();
    }

    public int calculateRouteDistance(Route route) {
        int totalDistance = 0;
        List<TrainStation> trainStationList = route.getStops();
        for (int i = 0; i < trainStationList.size() - 1; i = i + 1) {
            TrainStation station1 = trainStationList.get(i);
            TrainStation station2 = trainStationList.get(i + 1);
            totalDistance += calculateDistanceInKilometer(station1.getLatitude(), station1.getLongitude()
                    , station2.getLatitude(), station2.getLongitude());
        }
        return totalDistance;
    }

    public int calculateDistanceInKilometer(double userLat, double userLng, double venueLat, double venueLng) {

        double latDistance = Math.toRadians(userLat - venueLat);
        double lngDistance = Math.toRadians(userLng - venueLng);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(venueLat))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (int) (Math.round(AVERAGE_RADIUS_OF_EARTH_KM * c));
    }


}