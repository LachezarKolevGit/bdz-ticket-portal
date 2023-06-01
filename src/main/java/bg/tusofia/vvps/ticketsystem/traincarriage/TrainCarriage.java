package bg.tusofia.vvps.ticketsystem.traincarriage;

import bg.tusofia.vvps.ticketsystem.train.Train;
import bg.tusofia.vvps.ticketsystem.seat.Seat;
import bg.tusofia.vvps.ticketsystem.seat.SeatState;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "train_carriage", schema = "public")
public class TrainCarriage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "train_carriage_id_seq")
    @Setter(AccessLevel.NONE)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TrainCarriageType trainCarriageType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "train_id", updatable = false)
    private Train train;

    @OneToMany(mappedBy = "trainCarriage", cascade = CascadeType.ALL)
    private List<Seat> seats;
    private int totalSeats;

    public TrainCarriage(TrainCarriageType trainCarriageType, int totalSeats) {
        this.trainCarriageType = trainCarriageType;
        this.totalSeats = totalSeats;
        this.seats = Stream.generate(() -> new Seat(SeatState.AVAILABLE, this))
                .limit(totalSeats)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "TrainCarriage{" +
                "id=" + id +
                ", trainCarriageType=" + trainCarriageType +
                ", seats=" + seats +
                ", totalSeats=" + totalSeats +
                '}';
    }
}
