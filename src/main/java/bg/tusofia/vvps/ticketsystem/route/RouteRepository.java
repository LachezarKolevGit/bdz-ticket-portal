package bg.tusofia.vvps.ticketsystem.route;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends PagingAndSortingRepository<Route, Long>, CrudRepository<Route, Long> {

    List<Route> findAll();
}