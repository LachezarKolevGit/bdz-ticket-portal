package bg.tusofia.vvps.ticketsystem.train;

import bg.tusofia.vvps.ticketsystem.route.Route;
import bg.tusofia.vvps.ticketsystem.traincarriage.TrainCarriage;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "train", schema="public")
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "train_id_seq")
    @Setter(AccessLevel.NONE)
    private Long id;
    @OneToMany(mappedBy = "train", cascade = CascadeType.PERSIST)
    private Set<TrainCarriage> formedByTrainCarriages = new HashSet<>();
    private LocalDateTime departingAt;
    private LocalDateTime arrivingAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    private Route route;

    public Train(Set<TrainCarriage> formedByTrainCarriages, LocalDateTime departingAt, LocalDateTime arrivingAt) {
        if (formedByTrainCarriages != null) {
            for (TrainCarriage trainCarriage : formedByTrainCarriages) {
                trainCarriage.setTrain(this);
            }
        }
        this.formedByTrainCarriages = formedByTrainCarriages;
        this.departingAt = departingAt;
        this.arrivingAt = arrivingAt;
    }

    public Train(Set<TrainCarriage> trainCarriageList, LocalDateTime departingAt, LocalDateTime arrivingAt, Route route) {
    }

    public void setRoute(Route route) {
        if (this.route != null) {
            throw new IllegalArgumentException("Route is already set to this train");
        }
        this.route = route;
    }

    @Override
    public String toString() {
        return "Train{" +
                "id=" + id +
                ", formedByTrainCarriages=" + formedByTrainCarriages + //might trigger the lazy loading
                ", departingAt=" + departingAt +
                ", arrivingAt=" + arrivingAt +
                '}';
    }

    public void assignRoute(Route route) {
        if (this.route != null) {
            throw new IllegalArgumentException("Train is already assigned to route");
        }
        this.route = route;
    }
}
