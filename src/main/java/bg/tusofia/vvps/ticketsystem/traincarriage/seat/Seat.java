package bg.tusofia.vvps.ticketsystem.traincarriage.seat;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Embeddable
@Table(name = "seat")
public class Seat {
    @Enumerated
    private SeatState seatState;

    public Seat() {
    }

    public Seat(SeatState seatState) {
        this.seatState = seatState;
    }

    public SeatState getSeatState() {
        return seatState;
    }

    public void setSeatState(SeatState seatState) {
        this.seatState = seatState;
    }

    @Override
    public String toString() {
        return "Seat{" + ", seatState=" + seatState + '}';
    }
}
