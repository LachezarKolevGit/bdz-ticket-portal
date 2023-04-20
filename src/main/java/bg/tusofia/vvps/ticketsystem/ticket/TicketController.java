package bg.tusofia.vvps.ticketsystem.ticket;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TicketController {

    @GetMapping("/checkout")
    public String purchaseTicket(@RequestParam(value = "trainCarriageId", required = false) Long trainCarriageId,
                                 @RequestParam(value = "seatId", required = false) Long seatId, Model model) {
        model.addAttribute("trainCarriageId");
        model.addAttribute("seatId");

        return "ticket_checkout_page";  //must be a path that we are returning
    }
}
