package bg.tusofia.vvps.ticketsystem.route;

import bg.tusofia.vvps.ticketsystem.train.Train;
import bg.tusofia.vvps.ticketsystem.trainstation.TrainStation;

import java.util.List;
import java.util.Set;

public record RouteDTO(List<TrainStation> trainStations, Set<Train> trains) {

}
