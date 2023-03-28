package bg.tusofia.vvps.ticketsystem.route;


import bg.tusofia.vvps.ticketsystem.train.Train;
import bg.tusofia.vvps.ticketsystem.trainstation.TrainStation;
import jakarta.persistence.*;

import java.util.*;

@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(mappedBy = "route", cascade = CascadeType.DETACH)
    private List<TrainStation> stops = new ArrayList<>();
    @OneToMany(mappedBy = "route", cascade = CascadeType.DETACH)  //probably exception incoming
    private Set<Train> trains = new HashSet<>();

    public Route() {
    }

    public Route(List<TrainStation> stops, Set<Train> trains) {
        this.stops = stops;
        this.trains = trains;
    }

    public List<TrainStation> getStops() {
        return Collections.unmodifiableList(stops);
    }

    public void setStops(List<TrainStation> stops) {
        this.stops = stops;
    }

    public Set<Train> getTrains() {
        return Collections.unmodifiableSet(trains);
    }

    public void setTrains(Set<Train> trains) {
        this.trains = trains;
    }

    //good practise to initialize collections right away
    //has to be ordered which needs to be fixed

}
