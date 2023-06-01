package bg.tusofia.vvps.ticketsystem.trainstation;

import bg.tusofia.vvps.ticketsystem.route.Route;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(schema="public")
public class TrainStation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "train_station_id_seq")
    @Setter(AccessLevel.NONE)
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", updatable = false)
    private Route route;

    public TrainStation(String name, Double latitude, Double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
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
