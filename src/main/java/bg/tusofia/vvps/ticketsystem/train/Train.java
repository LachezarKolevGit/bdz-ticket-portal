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

    @OneToMany(mappedBy = "train", cascade = CascadeType.DETACH)
    private Set<TrainCarriage> formedByTrainCarriages = new HashSet<>();  // ?? exception suspicion

    private LocalDateTime departingAt;
    private LocalDateTime arrivingAt;
    @ManyToOne(fetch = FetchType.LAZY ,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "route_id")
    private Route route;

    public Train() {
    }

    public Train(Set<TrainCarriage> formedByTrainCarriages, LocalDateTime departingAt, LocalDateTime arrivingAt, Route route) {
        this.formedByTrainCarriages = formedByTrainCarriages;
        this.departingAt = departingAt;
        this.arrivingAt = arrivingAt;
        this.route = route;
    }

    public Long getId() {
        return id;
    }

    public Set<TrainCarriage> getFormedByTrainCarriages() {
        return formedByTrainCarriages;
    }

    public void setFormedByTrainCarriages(Set<TrainCarriage> formedByTrainCarriages) {
        this.formedByTrainCarriages = formedByTrainCarriages;
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
        this.route = route;
    }

    @Override
    public String toString() {
        return "Train{" +
                "id=" + id +
                ", formedByTrainCarriages=" + formedByTrainCarriages +
                ", departingAt=" + departingAt +
                ", arrivingAt=" + arrivingAt +
                ", route=" + route +
                '}';
    }
}
