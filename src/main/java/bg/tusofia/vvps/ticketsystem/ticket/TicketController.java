package bg.tusofia.vvps.ticketsystem.ticket;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/checkout")
    public String purchaseTicket(Model model) {
        model.addAttribute("ticket", new TicketDTO());
        return "ticket/ticket_checkout_page";
    }

    /**
     * Currently the system allows only one ticket per checkout request, work on that
     *
     * @param model
     * @param ticketDTO
     * @return
     */
    @PostMapping("/checkout")
    public String purchaseTicket(Model model, @ModelAttribute("ticket") TicketDTO ticketDTO) {
        Long ticketId = ticketService.paymentProcess(ticketDTO);
        model.addAttribute("ticketId", ticketId);
        return "ticket/ticket_purchased_successfully";
    }

    @GetMapping("/reservation")
    public String reserveTicketPage(Model model) {
        model.addAttribute("ticket", new TicketDTO());
        return "ticket/reserve_ticket_page";
    }

    @PostMapping("/reservation")
    public String reserveTicket(Model model, @ModelAttribute("ticket") TicketDTO ticketDTO) {
        Long ticketId = ticketService.reserveTicket(ticketDTO);
        model.addAttribute("ticketId", ticketId);
        return "ticket/ticket_reserved_successfully";  //must be a path that we are returning
    }

    @GetMapping("/reservations")
    public String getAllReservations(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {
        Page<Ticket> reservedTicketsPage = ticketService.getAllReservations(page);
        model.addAttribute("reservedTickets", reservedTicketsPage);

        int totalPages = reservedTicketsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "ticket/reservations_all_by_user";
    }

    @DeleteMapping("/reservation/{id}")
    public String cancelReservation(@PathVariable(name = "id") String ticketId) {
        ticketService.deleteReservation(Long.valueOf(ticketId));
        return "ticket/reservation_deleted_successfully";
    }

    @PostMapping("/reservation/{id}")
    public String editReservation(Model model,
                                  @PathVariable(name = "id") String ticketId,
                                  @RequestParam(name = "seatId") Long newSeatId) {
        Long editedTicketId = ticketService.editReservation(Long.valueOf(ticketId), newSeatId);
        model.addAttribute("ticketId", editedTicketId);
        return "ticket/ticket_reservation_edit_successfully";
    }


}
