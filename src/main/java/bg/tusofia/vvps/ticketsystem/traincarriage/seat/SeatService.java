package bg.tusofia.vvps.ticketsystem.traincarriage.seat;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public void markSeatAsSold(Long seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new EntityNotFoundException("Seat was not found"));
        seat.setSeatState(SeatState.SOLD);
        seatRepository.save(seat);
    }

    public void markSeatAsReserved(Long seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new EntityNotFoundException("Seat was not found"));
        seat.setSeatState(SeatState.RESERVED);
        seatRepository.save(seat);
    }

}
