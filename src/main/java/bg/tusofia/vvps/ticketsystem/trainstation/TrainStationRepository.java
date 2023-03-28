package bg.tusofia.vvps.ticketsystem.trainstation;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainStationRepository extends CrudRepository<TrainStation, Long> {
}