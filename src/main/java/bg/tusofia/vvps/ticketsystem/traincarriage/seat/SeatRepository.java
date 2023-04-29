package bg.tusofia.vvps.ticketsystem.traincarriage.seat;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends PagingAndSortingRepository<Seat, Long>, CrudRepository<Seat, Long> {


}
