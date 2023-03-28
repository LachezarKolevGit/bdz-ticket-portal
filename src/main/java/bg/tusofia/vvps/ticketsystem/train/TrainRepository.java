package bg.tusofia.vvps.ticketsystem.train;

import bg.tusofia.vvps.ticketsystem.trainstation.TrainStation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface TrainRepository extends PagingAndSortingRepository<Train, Long> {

    @Query("SELECT t FROM Train t where t.route = (SELECT r.train FROM ROUTE r where r.stops = (SELECT route FROM TrainStation ts ) ) ?1")
    List<Train> getTrainsByDestination(String destination, Pageable pageable); //use paging

    List<Train> getTrainsByArrivingAt(LocalTime arrivingAt);

}
