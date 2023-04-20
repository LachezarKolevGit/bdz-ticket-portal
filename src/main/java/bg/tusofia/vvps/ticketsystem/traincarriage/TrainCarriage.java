package bg.tusofia.vvps.ticketsystem.traincarriage;

import bg.tusofia.vvps.ticketsystem.train.Train;
import bg.tusofia.vvps.ticketsystem.traincarriage.seat.Seat;
import bg.tusofia.vvps.ticketsystem.traincarriage.seat.SeatState;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "train_carriage")
public class TrainCarriage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "train_carriage_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private TrainCarriageType trainCarriageType;

    @ManyToOne(fetch = FetchType.LAZY) //cascade type ??
    @JoinColumn(name = "train_id")
    private Train train;

    @OneToMany(mappedBy = "trainCarriage", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Seat> seats;  //initialize it with totalSeats
    private int totalSeats;

    public TrainCarriage() {
    }

    public TrainCarriage(TrainCarriageType trainCarriageType, int totalSeats) {
        this.trainCarriageType = trainCarriageType;
        this.totalSeats = totalSeats;
        this.seats = Stream.generate(() -> new Seat(SeatState.AVAILABLE, this))
                        .limit(totalSeats)
                                .collect(Collectors.toList());
        System.out.println("Seats in copnstr : " + seats);
    }

    public Long getId() {
        return id;
    }

    public TrainCarriageType getTrainCarriageType() {
        return trainCarriageType;
    }

    public TrainCarriageType getCarriageType() {
        return trainCarriageType;
    }

    public void setCarriageType(TrainCarriageType trainCarriageType) {
        this.trainCarriageType = trainCarriageType;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    @Override
    public String toString() {
        return "TrainCarriage{" +
                "id=" + id +
                ", trainCarriageType=" + trainCarriageType +
                ", train=" + train +
                ", seats=" + seats +
                ", totalSeats=" + totalSeats +
                '}';
    }
}
