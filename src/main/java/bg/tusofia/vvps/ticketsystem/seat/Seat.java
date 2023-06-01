package bg.tusofia.vvps.ticketsystem.seat;

import bg.tusofia.vvps.ticketsystem.ticket.Ticket;
import bg.tusofia.vvps.ticketsystem.traincarriage.TrainCarriage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "seat", schema="public")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seat_id_seq")
    private Long id;

    @Enumerated
    private SeatState seatState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "train_carriage_id", updatable = false)
    private TrainCarriage trainCarriage;

    @OneToOne(fetch = FetchType.LAZY) //check if it loads correctly
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    public Seat(SeatState seatState, TrainCarriage trainCarriage) {
        this.seatState = seatState;
        this.trainCarriage = trainCarriage;
    }

    @Override
    public String toString() {
        return "Seat{" + ", seatState=" + seatState + '}';
    }

    public void markSeatAsSold(Ticket ticket) { //add check first if the ticket is not already set
        if (this.ticket != null) {
            throw new IllegalArgumentException("Seat is not available");
        }
        this.ticket = ticket;
        this.seatState = SeatState.SOLD;
        ticket.setSeat(this);
    }

    public void markSeatAsReserved(Ticket ticket) { //add check first if the ticket is not already set
        if (this.ticket != null) {
            throw new IllegalArgumentException("Seat is not available");
        }
        this.ticket = ticket;
        this.seatState = SeatState.RESERVED;
        ticket.setSeat(this);
    }

    public void markSeatAsAvailable(Ticket ticket) { //add check first if the ticket is not already set
        this.ticket = null;
        this.seatState = SeatState.AVAILABLE;
        ticket.setSeat(null);
    }
}
