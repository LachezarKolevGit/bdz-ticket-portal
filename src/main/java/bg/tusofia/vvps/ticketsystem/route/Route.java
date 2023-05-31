package bg.tusofia.vvps.ticketsystem.route;


import bg.tusofia.vvps.ticketsystem.train.Train;
import bg.tusofia.vvps.ticketsystem.trainstation.TrainStation;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(schema="public")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "route_id_seq")
    private Long id;
    @OneToMany(mappedBy = "route", cascade = CascadeType.PERSIST)
    @OrderColumn()
    private List<TrainStation> trainStations = new ArrayList<>();
    @OneToMany(mappedBy = "route", cascade = CascadeType.PERSIST)  //probably exception incoming
    private Set<Train> trains = new HashSet<>();

    public Route() {
    }

    public Route(List<TrainStation> trainStations, Set<Train> trains) {
        if (trainStations != null) {
            for (TrainStation trainStation : trainStations) {
                trainStation.setRoute(this);
                this.trainStations = trainStations;
            }
        }
        if (trains != null) {
            for (Train train : trains) {
                train.setRoute(this);
            }
            this.trains = trains;
        }
    }

    public Long getId() {
        return id;
    }

    public List<TrainStation> getStops() {
        return Collections.unmodifiableList(trainStations);
    }

    public Set<Train> getTrains() {
        return Collections.unmodifiableSet(trains);
    }

    public void addStop(TrainStation trainStation) { // call when initializing a train station
        //exception checks
        trainStations.add(trainStation);
        trainStation.setRoute(this);
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", trainStations=" + trainStations +
                '}';
    }
}
