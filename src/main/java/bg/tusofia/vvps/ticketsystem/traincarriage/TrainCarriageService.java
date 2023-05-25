package bg.tusofia.vvps.ticketsystem.traincarriage;

import bg.tusofia.vvps.ticketsystem.traincarriage.seat.Seat;
import bg.tusofia.vvps.ticketsystem.traincarriage.seat.SeatRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Transactional(readOnly = true)
@Service
public class TrainCarriageService {
    private final TrainCarriageRepository trainCarriageRepository;
    private final SeatRepository seatRepository;

    public TrainCarriageService(TrainCarriageRepository trainCarriageRepository,
                                SeatRepository seatRepository) {
        this.trainCarriageRepository = trainCarriageRepository;
        this.seatRepository = seatRepository;
    }

    @Transactional(readOnly = false)
    public Long saveNewTrainCarriage(TrainCarriageDTO trainCarriageDTO) {
        TrainCarriage trainCarriage = new TrainCarriage(trainCarriageDTO.getTrainCarriageType(), trainCarriageDTO.getTotalSeats());
        System.out.println(trainCarriage);
        trainCarriageRepository.save(trainCarriage);
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
        return seatRepository.findById(Long.valueOf(seatId))
                .orElseThrow(() -> new EntityNotFoundException("Seat with that id was not found"));

    }

    public Page<TrainCarriage> getAllTrainCarriages(int page) {
        int pageSize = 10;
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return trainCarriageRepository.findAll(pageRequest);
    }

    public List<TrainCarriage> getAllTrainCarriages() {
        return trainCarriageRepository.findAll();
    }

    public double getTrainCarriageClassPriceMultiplier(Long seatId) {
        TrainCarriage trainCarriage = trainCarriageRepository.findTrainCarriageBySeatId(seatId)
                .orElseThrow(() -> new EntityNotFoundException("Train Carriage was not found"));

        return trainCarriage.getCarriageType().getMultiplier();
    }
}
