package bg.tusofia.vvps.ticketsystem.train;

import java.time.LocalTime;

public class Train {

    private Long id;
    private LocalTime departingTime;
    private LocalTime arrivingTime;
    private String departingFrom;
    private String arrivingAt;
    private Double price;

    public Train(Long id, LocalTime departingTime, LocalTime arrivingTime, String departingFrom, String arrivingAt) {
        this.id = id;
        this.departingTime = departingTime;
        this.arrivingTime = arrivingTime;
        this.departingFrom = departingFrom;
        this.arrivingAt = arrivingAt;
    }
}
