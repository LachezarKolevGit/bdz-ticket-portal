package bg.tusofia.vvps.ticketsystem.train;

import bg.tusofia.vvps.ticketsystem.route.RouteService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class TrainService {
    private TrainRepository trainRepository;
    private RouteService routeService;

    public TrainService(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    public Page<Train> getTrainByArrivalStation(String destination) {
        
        Page<Train> trainList = trainRepository.getTrainsByDestination(destination);
        return trainList;
    }

    public List<Train> getTrainByArrivalTime(LocalTime arrivalTime) {
        List<Train> trainList = trainRepository.getTrainsByArrivingAt(arrivalTime);
        return trainList;
    }




}
