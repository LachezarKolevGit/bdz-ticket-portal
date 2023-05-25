package bg.tusofia.vvps.ticketsystem.train;

import bg.tusofia.vvps.ticketsystem.route.Route;
import bg.tusofia.vvps.ticketsystem.route.RouteService;
import bg.tusofia.vvps.ticketsystem.traincarriage.TrainCarriage;
import bg.tusofia.vvps.ticketsystem.traincarriage.TrainCarriageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class TrainService {
    public static final double DIESEL_PRICE = 2.80;
    public static final int PAGE_SIZE = 10;
    private final TrainRepository trainRepository;
    private final RouteService routeService;
    private final TrainCarriageRepository trainCarriageRepository;

    public List<Train> getTrainByArrivalStationAndDepartureTime(String destination, LocalDateTime departureDateTime) {
        return trainRepository.getTrainsByDestinationAndDateTime(destination, departureDateTime);
    }

    public Train getTrainById(Long id) {
        return trainRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Train with that id was not found"));
    }

    public Page<Train> getAllTrains(int page) {
        PageRequest pageRequest = PageRequest.of(page, PAGE_SIZE);
        return trainRepository.findAll(pageRequest);
    }

    public double calculateBasePrice(Long trainId) { //calculates the base ticket's price
        Optional<Train> optionalTrain = trainRepository.findById(trainId);
        if (optionalTrain.isEmpty()) {
            throw new EntityNotFoundException();
        }
        Train train = optionalTrain.get();
        Route routeId = train.getRoute();
        int distance = routeService.calculateRouteDistance(routeId);
        double priceOfFuel = distance * DIESEL_PRICE;
        int totalSeats = 0;
        Set<TrainCarriage> trainCarriageSet = train.getFormedByTrainCarriages();
        for (TrainCarriage trainCarriage : trainCarriageSet) {
            totalSeats += trainCarriage.getTotalSeats();
        }
        return priceOfFuel / totalSeats;
    }

    public Long createTrain(TrainDTO trainDTO) {
        if (trainDTO == null) {
            throw new IllegalArgumentException("Train can't be null");
        }
        Integer[] ids = trainDTO.getTrainCarriagesId();
        Set<TrainCarriage> trainCarriageList = new HashSet<>();
        for (Integer id : ids) {
            TrainCarriage trainCarriage = trainCarriageRepository.findById(Long.valueOf(id))
                    .orElseThrow(() -> new EntityNotFoundException("Train carriage with id " + id + " was not found"));
            trainCarriageList.add(trainCarriage);

        }
        Route route = null;
        if (trainDTO.getRouteId() != null) {
            route = routeService.getRoute(Long.valueOf(trainDTO.getRouteId()));
        }
        Train train = new Train(trainCarriageList, trainDTO.getDepartingAt(), trainDTO.getArrivingAt(), route);

        trainRepository.save(train);
        return train.getId();
    }

    public void assignTrainToRoute(String trainId, String routeId) {
        Optional<Train> trainOptional = trainRepository.findById(Long.valueOf(trainId));
        if (trainOptional.isEmpty()) {
            throw new EntityNotFoundException("Train with that id was not found");
        }
        Route route = routeService.getRoute(Long.valueOf(routeId));
        Train train = trainOptional.get();
        train.assignRoute(route);
        trainRepository.save(train);
    }

}
