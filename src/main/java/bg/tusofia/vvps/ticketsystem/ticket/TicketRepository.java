package bg.tusofia.vvps.ticketsystem.ticket;

import bg.tusofia.vvps.ticketsystem.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends PagingAndSortingRepository<Ticket, Long>, CrudRepository<Ticket, Long> {
     Page<Ticket> findAllByTicketStateAndUser(TicketState ticketState, User user, PageRequest pageRequest);
     Optional<Ticket> findTicketBySeatId(Long seatId);
}
