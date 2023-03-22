package bg.tusofia.vvps.ticketsystem.traincarriage;

import jakarta.persistence.*;

@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated
    private SeatState seatState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "train_carriage_id")
    private TrainCarriage trainCarriage;

    public Seat() {
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
}
