package bg/tusofia/vvps/ticketsystem/trainstation;
@Service
public class TrainStationService{
    private TrainStationRepository trainStationRepository; 

    public TrainStationService(TrainStationRepository trainStationRepository){
        this.trainStationRepository =  trainStationRepository;
    }

    public void saveTrainStation(TrainStation trainStation){
        trainStationRepository.save(trainStation);
    }


}