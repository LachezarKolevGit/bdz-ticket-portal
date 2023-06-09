package bg.tusofia.vvps.ticketsystem.route;

import bg.tusofia.vvps.ticketsystem.trainstation.TrainStation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class RouteService {
    public static final double AVERAGE_RADIUS_OF_EARTH_KM = 6371;

    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public Route getRoute(Long routeId) {
        Optional<Route> routeOptional = routeRepository.findById(routeId);
        if (routeOptional.isEmpty()) {
            throw new EntityNotFoundException("Route with that id was not found");
        }
        return routeOptional.get();
    }

    public Page<Route> getRoutes(int page) {
        int pageSize = 10;
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return routeRepository.findAll(pageRequest);
    }

    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    public Long createNewRoute(RouteDTO routeDTO) {
        if (routeDTO == null) {
            throw new IllegalArgumentException("Route can't be null");  //change with @NotNull
        }
        Route route = new Route(routeDTO.trainStations(), routeDTO.trains());
        routeRepository.save(route);
        return route.getId();
    }

    public int calculateRouteDistance(Route route) {
        int totalDistance = 0;
        List<TrainStation> trainStationList = route.getTrainStations();
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