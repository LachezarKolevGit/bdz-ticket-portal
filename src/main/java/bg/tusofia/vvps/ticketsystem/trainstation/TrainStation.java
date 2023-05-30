package bg.tusofia.vvps.ticketsystem.trainstation;

import bg.tusofia.vvps.ticketsystem.route.Route;
import jakarta.persistence.*;

@Entity
public class TrainStation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "train_station_id_seq")
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", updatable = false)
    private Route route;

    public TrainStation() {
    }

    public TrainStation(String name, Double latitude, Double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
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
