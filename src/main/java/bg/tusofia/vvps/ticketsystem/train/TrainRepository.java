package bg.tusofia.vvps.ticketsystem.train;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface TrainRepository extends PagingAndSortingRepository<Train, Long>, CrudRepository<Train, Long> {

    @Query(value = "SELECT * FROM train WHERE train.route_id = (SELECT id FROM route WHERE route.id = (SELECT route_id FROM train_station WHERE train_station.name = :destination ORDER BY train_stations_order DESC LIMIT 1)) AND train.arriving_at = :arrivalDateTime;",
            nativeQuery = true)
    Page<Train> getTrainsByDestination(@Param("destination") String destination, @Param("arrivalDateTime") LocalDateTime arrivalDateTime, Pageable pageable); //use paging

    List<Train> getTrainsByArrivingAt(LocalTime arrivingAt);

}
