package bg.tusofia.vvps.ticketsystem.traincarriage;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainCarriageRepository extends PagingAndSortingRepository<TrainCarriage, Long>, CrudRepository<TrainCarriage, Long> {

    @Query("SELECT tc FROM TrainCarriage tc WHERE tc = (SELECT s.trainCarriage FROM Seat s WHERE s.id = :seatId)")
    Optional<TrainCarriage> findTrainCarriageBySeatId(@Param("seatId") Long seatId);

    List<TrainCarriage> findAll();
}
