package bg.tusofia.vvps.ticketsystem.train;

import bg.tusofia.vvps.ticketsystem.route.Route;
import bg.tusofia.vvps.ticketsystem.traincarriage.TrainCarriage;
import bg.tusofia.vvps.ticketsystem.trainstation.TrainStation;
import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "train", cascade = CascadeType.DETACH)
    private Set<TrainCarriage> formedByTrainCarriages = new HashSet<>();

    private LocalTime departingAt;
    private LocalTime arrivingAt;
    @ManyToOne(fetch = FetchType.LAZY ,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "route_id")
    private Route route;

    public Train() {
    }

    public Train(Set<TrainCarriage> formedByTrainCarriages, LocalTime departingAt, LocalTime arrivingAt, Route route) {
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

    public LocalTime getDepartingAt() {
        return departingAt;
    }

    public void setDepartingAt(LocalTime departingAt) {
        this.departingAt = departingAt;
    }

    public LocalTime getArrivingAt() {
        return arrivingAt;
    }

    public void setArrivingAt(LocalTime arrivingAt) {
        this.arrivingAt = arrivingAt;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

}
