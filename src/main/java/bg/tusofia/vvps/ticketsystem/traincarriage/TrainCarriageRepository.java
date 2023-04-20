package bg.tusofia.vvps.ticketsystem.traincarriage;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainCarriageRepository extends PagingAndSortingRepository<TrainCarriage, Long>, CrudRepository<TrainCarriage, Long> {
}
