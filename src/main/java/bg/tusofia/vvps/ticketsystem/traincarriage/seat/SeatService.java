package bg.tusofia.vvps.ticketsystem.traincarriage.seat;

import bg.tusofia.vvps.ticketsystem.train.Train;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public Train findTrainBySeatId(Long seatId){
       return seatRepository.findTrainBySeatId(seatId)
               .orElseThrow(() -> new EntityNotFoundException("Train was not found by the provided seat id"));
    }

    public Seat markSeatAsSold(Long seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new EntityNotFoundException("Seat was not found"));
        seat.setSeatState(SeatState.SOLD);
        seatRepository.save(seat);
        return seat;
    }

    public Seat markSeatAsReserved(Long seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new EntityNotFoundException("Seat was not found"));
        seat.setSeatState(SeatState.RESERVED);
        seatRepository.save(seat);

        return seat;
    }

    public Seat markSeatAsAvailable(Long seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new EntityNotFoundException("Seat was not found"));
        seat.setSeatState(SeatState.AVAILABLE);
        seatRepository.save(seat);

        return seat;
    }
}
