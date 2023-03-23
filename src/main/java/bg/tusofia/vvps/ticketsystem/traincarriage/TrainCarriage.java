package bg.tusofia.vvps.ticketsystem.traincarriage;

import bg.tusofia.vvps.ticketsystem.train.Train;
import jakarta.persistence.*;

import java.util.List;
@Entity
public class TrainCarriage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "train_carriage_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private CarriageType carriageType;

    @ManyToOne(fetch = FetchType.LAZY) //cascade type ??
    @JoinColumn(name = "train_id")
    private Train train;

    @OneToMany(mappedBy = "trainCarriage", cascade = CascadeType.ALL)
    private List<Seat> seats;  //initialize it with totalSeats
    private int totalSeats;
    private int remainingSeats;
    private float seatPrice;

    public TrainCarriage() {
    }

    public TrainCarriage(CarriageType carriageType, Train train, int totalSeats, int remainingSeats) {
        this.id = id;
        this.carriageType = carriageType;
        this.train = train;
        this.totalSeats = totalSeats;
        this.remainingSeats = remainingSeats;
    }
}
