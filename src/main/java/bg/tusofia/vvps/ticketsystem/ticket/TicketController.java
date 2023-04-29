package bg.tusofia.vvps.ticketsystem.ticket;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        Long ticketId = ticketService.paymentProcess(ticketDTO.getTrainId(),
                ticketDTO.getSeatId(), ticketDTO.getNumberOfTickets());
        model.addAttribute("ticketId", ticketId);
        return "ticket/ticket_purchased_successfully";
    }

    @GetMapping("/reserve")
    public String reserveTicketPage(Model model) {
        model.addAttribute("ticket", new TicketDTO());
        return "ticket/reserve_ticket_page";
    }

    @PostMapping("/reserve")
    public String reserveTicket(Model model, @ModelAttribute("ticket") TicketDTO ticketDTO) {
        /*Long ticketId = ticketService.reserveTicket(ticketDTO.getTrainId(),
                ticketDTO.getSeatId(), ticketDTO.getNumberOfTickets());
        model.addAttribute("ticketId", ticketId);*/
        return "ticket/ticket_purchased_successfully";  //must be a path that we are returning
    }
}
