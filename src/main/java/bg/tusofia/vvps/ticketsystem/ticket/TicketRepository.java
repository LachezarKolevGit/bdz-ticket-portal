package bg.tusofia.vvps.ticketsystem.ticket;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends PagingAndSortingRepository<Ticket, Long>, CrudRepository<Ticket, Long> {
}
