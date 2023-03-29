package bg.tusofia.vvps.ticketsystem.traincarriage.seat;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends CrudRepository<Seat, Long> {

}