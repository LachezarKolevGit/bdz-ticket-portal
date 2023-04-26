package bg.tusofia.vvps.ticketsystem.train;

import bg.tusofia.vvps.ticketsystem.route.Route;
import bg.tusofia.vvps.ticketsystem.traincarriage.TrainCarriage;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "train")
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToMany(mappedBy = "train", cascade = CascadeType.PERSIST)
    private Set<TrainCarriage> formedByTrainCarriages = new HashSet<>();  // ?? exception suspicion

    private LocalDateTime departingAt;
    private LocalDateTime arrivingAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    private Route route;

    public Train() {
    }

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

    public Long getId() {
        return id;
    }

    public Set<TrainCarriage> getFormedByTrainCarriages() {
        return formedByTrainCarriages;
    }

    public LocalDateTime getDepartingAt() {
        return departingAt;
    }

    public void setDepartingAt(LocalDateTime departingAt) {
        this.departingAt = departingAt;
    }

    public LocalDateTime getArrivingAt() {
        return arrivingAt;
    }

    public void setArrivingAt(LocalDateTime arrivingAt) {
        this.arrivingAt = arrivingAt;
    }

    public Route getRoute() {
        return route;
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
                ", formedByTrainCarriages=" + formedByTrainCarriages +
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
