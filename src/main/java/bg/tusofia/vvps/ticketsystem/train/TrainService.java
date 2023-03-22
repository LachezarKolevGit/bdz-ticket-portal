package bg.tusofia.vvps.ticketsystem.train;

import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class TrainService {

    private TrainRepository trainRepository;

    public TrainService(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    public List<Train> getTrainByArrivalStation(String destination) {
        List<Train> trainList = trainRepository.getTrainsByDestination(destination);
        return trainList;
    }

    public List<Train> getTrainByArrivalTime(LocalTime arrivalTime) {
        List<Train> trainList = trainRepository.getTrainsByArrivingAt(arrivalTime);
        return trainList;
    }


}
