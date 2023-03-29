package bg.tusofia.vvps.ticketsystem.traincarriage;

import bg.tusofia.vvps.ticketsystem.train.Train;
import bg.tusofia.vvps.ticketsystem.traincarriage.seat.Seat;
import jakarta.persistence.*;

import java.util.List;
@Entity
public class TrainCarriage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "train_carriage_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private TrainCarriageType trainCarriageType;

    @ManyToOne(fetch = FetchType.LAZY) //cascade type ??
    @JoinColumn(name = "train_id")
    private Train train;

    @OneToMany(mappedBy = "trainCarriage", cascade = CascadeType.ALL)
    private List<Seat> seats;  //initialize it with totalSeats
    private int totalSeats;
    //private int remainingSeats;

    private double seatBasePrice;

    public TrainCarriage() {
    }

    public TrainCarriage(TrainCarriageType trainCarriageType, Train train, int totalSeats) {
        this.trainCarriageType = trainCarriageType;
        this.train = train;
        this.totalSeats = totalSeats;
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

    public double getSeatBasePrice() {
        return seatBasePrice;
    }

    public void setSeatBasePrice(double seatBasePrice) {
        this.seatBasePrice = seatBasePrice;
    }
}
