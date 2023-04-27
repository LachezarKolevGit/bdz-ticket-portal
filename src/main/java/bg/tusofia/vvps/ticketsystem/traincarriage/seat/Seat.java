package bg.tusofia.vvps.ticketsystem.traincarriage.seat;

import bg.tusofia.vvps.ticketsystem.ticket.Ticket;
import bg.tusofia.vvps.ticketsystem.traincarriage.TrainCarriage;
import jakarta.persistence.*;

@Entity
@Table(name = "seat")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Enumerated
    private SeatState seatState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", updatable = false)
    private TrainCarriage trainCarriage;

    @OneToOne
    private Ticket ticket;
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
