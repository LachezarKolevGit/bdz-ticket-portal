package bg.tusofia.vvps.ticketsystem.trainstation;

import bg.tusofia.vvps.ticketsystem.train.TrainStationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
@Service
public class TrainStationService {

    private final TrainStationRepository trainStationRepository;

    public TrainStationService(TrainStationRepository trainStationRepository) {
        this.trainStationRepository = trainStationRepository;
    }

    public Page<TrainStation> getAllTrainStations(int page) {
        int sizeOfPage = 10;
        PageRequest pageRequest = PageRequest.of(page, sizeOfPage);
        return trainStationRepository.findAll(pageRequest);
    }

    public Long createNewTrainStation(TrainStationDTO trainStation) {
        if (trainStation == null) {
            throw new IllegalArgumentException("Train Station can't be null");
        }
        TrainStation newTrainStation = new TrainStation(trainStation.name(), trainStation.latitude(), trainStation.longitude());
        trainStationRepository.save(newTrainStation);
        return newTrainStation.getId();
    }
}
