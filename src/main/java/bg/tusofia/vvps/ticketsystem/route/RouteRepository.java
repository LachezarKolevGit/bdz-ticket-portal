package bg.tusofia.vvps.ticketsystem.route;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends PagingAndSortingRepository<Route, Long>, CrudRepository<Route, Long> {


}