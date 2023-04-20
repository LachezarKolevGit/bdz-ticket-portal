package bg.tusofia.vvps.ticketsystem.trainstation;

import bg.tusofia.vvps.ticketsystem.route.Route;
import jakarta.persistence.*;

@Entity
//@Table(name = "train_station")
public class TrainStation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST) //more research for cascade
    @JoinColumn(name = "route_id")
    private Route route;

    public TrainStation() {
    }

    public TrainStation(String name, Double latitude, Double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        route = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return "TrainStation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
