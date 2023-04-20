package bg.tusofia.vvps.ticketsystem.traincarriage.seat;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "train_carriage_id")
    private TrainCarriage trainCarriage;

    public Seat() {
    }

    public Seat(SeatState seatState, TrainCarriage trainCarriage) {
        this.seatState = seatState;
        this.trainCarriage = trainCarriage;
    }

    public Long getId() {
        return id;
    }

    public SeatState getSeatState() {
        return seatState;
    }

    public void setSeatState(SeatState seatState) {
        this.seatState = seatState;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", seatState=" + seatState +
                //", trainCarriage=" + trainCarriage +
                '}';
    }
}
