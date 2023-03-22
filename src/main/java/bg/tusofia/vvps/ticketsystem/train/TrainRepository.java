package bg.tusofia.vvps.ticketsystem.train;

import bg.tusofia.vvps.ticketsystem.trainstation.TrainStation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface TrainRepository extends CrudRepository<Train, Long> {

    @Query("SELECT t FROM Train t where t.arrivalTrainStation = ?1")
    List<Train> getTrainsByDestination(String destination);

    List<Train> getTrainsByArrivingAt(LocalTime arrivingAt);

}
