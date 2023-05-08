package bg.tusofia.vvps.ticketsystem.train;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TrainRepository extends PagingAndSortingRepository<Train, Long>, CrudRepository<Train, Long> {

    @Query(value = "SELECT * FROM train WHERE train.route_id = (SELECT id FROM route WHERE route.id = (SELECT route_id FROM train_station WHERE train_station.name = :destination ORDER BY train_stations_order DESC LIMIT 1)) AND train.departing_at = :departureDateTime",
            nativeQuery = true)
    List<Train> getTrainsByDestinationAndDateTime(@Param("destination") String destination, @Param("departureDateTime") LocalDateTime departureDateTime);


}
