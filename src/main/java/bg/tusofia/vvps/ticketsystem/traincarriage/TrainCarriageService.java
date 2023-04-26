package bg.tusofia.vvps.ticketsystem.traincarriage;

import bg.tusofia.vvps.ticketsystem.traincarriage.seat.Seat;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrainCarriageService {
    private final TrainCarriageRepository trainCarriageRepository;

    public TrainCarriageService(TrainCarriageRepository trainCarriageRepository) {
        this.trainCarriageRepository = trainCarriageRepository;
    }

    public Long saveNewTrainCarriage(TrainCarriageDTO trainCarriageDTO) {
        TrainCarriage trainCarriage = new TrainCarriage(trainCarriageDTO.trainCarriageType(), trainCarriageDTO.totalSeats());
        trainCarriageRepository.save(trainCarriage);
        System.out.println(trainCarriage.getSeats());
        //seatRepository.saveAll(trainCarriage.getSeats());  //terrible, rework
        return trainCarriage.getId();
    }

    public TrainCarriage getTrainCarriage(String stringId) {
        Long id = Long.valueOf(stringId);
        Optional<TrainCarriage> trainCarriageOptional = trainCarriageRepository.findById(id);
        if (trainCarriageOptional.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return trainCarriageOptional.get();
    }

    public Seat getSeat(String seatId) {
        Long id = Long.valueOf(seatId);
        /*Optional<Seat> seatOptional = seatRepository.findById(id);
        if (seatOptional.isEmpty()) {
            throw new EntityNotFoundException();
        }
        Seat seat = seatOptional.get();
        Hibernate.initialize(seat);*/
        return null;
    }


    public Page<TrainCarriage> getAllTrains(int page) {
        int pageSize = 10;
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return trainCarriageRepository.findAll(pageRequest);
    }
}
