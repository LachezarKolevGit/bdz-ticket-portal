package bg.tusofia.vvps.ticketsystem.seat;

import bg.tusofia.vvps.ticketsystem.train.Train;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatRepository extends PagingAndSortingRepository<Seat, Long>, CrudRepository<Seat, Long> {
    @Query(value = "SELECT t FROM Train t WHERE t = (SELECT tc.train FROM TrainCarriage tc WHERE tc = (SELECT s.trainCarriage FROM Seat s WHERE s.id = :seatId))")
    Optional<Train> findTrainBySeatId(@Param("seatId")Long seatId);
}
