package bg.tusofia.vvps.ticketsystem.train;

import bg.tusofia.vvps.ticketsystem.route.Route;
import bg.tusofia.vvps.ticketsystem.route.RouteService;
import bg.tusofia.vvps.ticketsystem.traincarriage.TrainCarriage;
import bg.tusofia.vvps.ticketsystem.traincarriage.TrainCarriageRepository;
import bg.tusofia.vvps.ticketsystem.traincarriage.seat.Seat;
import bg.tusofia.vvps.ticketsystem.traincarriage.seat.SeatRepository;
import bg.tusofia.vvps.ticketsystem.traincarriage.seat.SeatState;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TrainService {
    private final TrainRepository trainRepository;

    private final RouteService routeService;

    private final TrainCarriageRepository trainCarriageRepository;

    private final SeatRepository seatRepository;

    public final static double DIESEL_PRICE = 2.80;

    public final static int PAGE_SIZE = 10;

    public TrainService(TrainRepository trainRepository, RouteService routeService, TrainCarriageRepository trainCarriageRepository, SeatRepository seatRepository) {
        this.trainRepository = trainRepository;
        this.routeService = routeService;
        this.trainCarriageRepository = trainCarriageRepository;
        this.seatRepository = seatRepository;
    }

    public Page<Train> getTrainByArrivalStation(String destination, int page) {

        Page<Train> trainsPage = trainRepository.getTrainsByDestination(destination, PageRequest.of(page, PAGE_SIZE));
        return trainsPage;
    }

    public List<Train> getTrainByArrivalTime(LocalTime arrivalTime) {
        List<Train> trainList = trainRepository.getTrainsByArrivingAt(arrivalTime);
        return trainList;
    }

    public double calculateBasePrice(Long trainId) { //calculates the base ticket's price
        Optional<Train> optionalTrain = trainRepository.findById(trainId);
        if (optionalTrain.isEmpty()) {
            throw new EntityNotFoundException();
        }
        Train train = optionalTrain.get();
        Route route = train.getRoute();
        int distance = routeService.calculateRouteDistance(route);
        double priceOfFuel = distance * DIESEL_PRICE;
        int totalSeats = 0;
        Set<TrainCarriage> trainCarriageSet = train.getFormedByTrainCarriages();
        for (TrainCarriage trainCarriage : trainCarriageSet) {
            totalSeats += trainCarriage.getTotalSeats();
        }
        return priceOfFuel / totalSeats;
    }

    public double getTrainCarriageClass(Long id) {
        Optional<TrainCarriage> trainCarriageOptional = trainCarriageRepository.findById(id);
        if (trainCarriageOptional.isEmpty()) {
            throw new EntityNotFoundException();
        }
        TrainCarriage trainCarriage = trainCarriageOptional.get();
        return trainCarriage.getCarriageType().getMultiplier();
    }

    public void changeSeatStatus(Long seatId) {
       /* Optional<TrainCarriage> trainCarriageOptional = trainCarriageRepository.findById(trainCarriageId);
        if (trainCarriageOptional.isEmpty()) {
            throw new EntityNotFoundException();
        }
        List<Seat> seatList = trainCarriageOptional.get().getSeats();
        seatList.get(seatId);*/

        Optional<Seat> seatOptional = seatRepository.findById(seatId);
        if (seatOptional.isEmpty()) {
            throw new EntityNotFoundException();
        }
        Seat seat = seatOptional.get();
        seat.setSeatState(SeatState.SOLD);
        seatRepository.save(seat);
    }

}
