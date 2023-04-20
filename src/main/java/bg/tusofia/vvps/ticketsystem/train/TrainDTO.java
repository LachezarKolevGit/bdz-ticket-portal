package bg.tusofia.vvps.ticketsystem.train;

import bg.tusofia.vvps.ticketsystem.route.Route;
import bg.tusofia.vvps.ticketsystem.traincarriage.TrainCarriage;

import java.time.LocalDateTime;
import java.util.Set;

public record TrainDTO(Set<TrainCarriage> formedByTrainCarriages,
                       LocalDateTime departingAt,
                       LocalDateTime arrivingAt, Route route) {
}
