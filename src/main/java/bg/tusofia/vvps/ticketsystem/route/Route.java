package bg.tusofia.vvps.ticketsystem.route;


import bg.tusofia.vvps.ticketsystem.train.Train;
import bg.tusofia.vvps.ticketsystem.trainstation.TrainStation;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(mappedBy = "route", cascade = CascadeType.DETACH)
    private Set<TrainStation> stops = new HashSet<>();
    @OneToMany(mappedBy = "route", cascade = CascadeType.DETACH)  //probably exception incoming
    private Set<Train> trains = new HashSet<>();

    public Route() {
    }

    public Route(Set<TrainStation> stops, Set<Train> trains) {
        this.stops = stops;
        this.trains = trains;
    }

    public Set<TrainStation> getStops() {
        return Collections.unmodifiableSet(stops);
    }

    public void setStops(Set<TrainStation> stops) {
        this.stops = stops;
    }

    public Set<Train> getTrains() {
        return trains;
    }

    public void setTrains(Set<Train> trains) {
        this.trains = trains;
    }

    //good practise to initialize collections right away
    //has to be ordered which needs to be fixed

}
