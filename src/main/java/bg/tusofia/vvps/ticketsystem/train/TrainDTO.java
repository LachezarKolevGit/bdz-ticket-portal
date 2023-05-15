package bg.tusofia.vvps.ticketsystem.train;

import java.time.LocalDateTime;

public record TrainDTO(Integer[] trainCarriagesId,
                       LocalDateTime departingAt,
                       LocalDateTime arrivingAt,
                       Integer routeId) {

    public TrainDTO(){
        this(null, null, null, null);
    }
}
