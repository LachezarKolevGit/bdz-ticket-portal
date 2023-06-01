package bg.tusofia.vvps.ticketsystem.ticket;

import bg.tusofia.vvps.ticketsystem.seat.Seat;
import bg.tusofia.vvps.ticketsystem.seat.SeatRepository;
import bg.tusofia.vvps.ticketsystem.seat.SeatState;
import bg.tusofia.vvps.ticketsystem.user.User;
import bg.tusofia.vvps.ticketsystem.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class TicketIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    UserService userService;

    @BeforeEach()
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void testUnauthorizedAccessGetRequest() throws Exception {
        this.mockMvc
                .perform(get("/reservation")
                        .with(csrf())
                        .with(anonymous()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testUnauthorizedAccessPostRequest() throws Exception {
        this.mockMvc
                .perform(post("/reservation")
                        .with(csrf())
                        .with(anonymous()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testUnauthorizedAccessPathVariable() throws Exception {
        this.mockMvc
                .perform(post("/reservation/" + "1")
                        .with(csrf())
                        .with(anonymous()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testSavedReservation() throws Exception {
        TicketDTO ticketDTO = new TicketDTO(1L, 5L, 1, null); //change the entry data
        this.mockMvc.perform(post("/ticket/reservation")
                        .with(user("client").roles("CLIENT"))
                        .param("seatId", ticketDTO.getSeatId().toString())
                        .param("numberOfTickets", String.valueOf(ticketDTO.getNumberOfTickets()))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("/ticket/ticket_reserved_successfully"))
                .andExpect(model().attributeExists("ticketId"));

        Seat seat = seatRepository.findById(ticketDTO.getSeatId())
                .orElseThrow(() -> new EntityNotFoundException("Seat with that id was not found"));
        User currentlyLoggedInUser = userService.getLoggedInUser();

        Ticket ticket = ticketRepository.findTicketBySeatId(seat.getId())
                .orElseThrow(() -> new EntityNotFoundException("Ticket was not found"));

        assertAll(
                () -> assertEquals(SeatState.RESERVED, seat.getSeatState(),
                        "The seat is expected to be reserved, but its not"),
                () -> assertTrue(currentlyLoggedInUser.getBoughtTickets().contains(ticket),
                        "The bought ticket is missing from the user's account")
        );
    }

    @Test
    void testDeleteReservation() throws Exception {
        TicketDTO ticketDTO = new TicketDTO(1L, 5L, 1, null); //change the entry data
        this.mockMvc.perform(post("/reservation")
                        .with(user("client").roles("CLIENT"))
                        .param("seatId", ticketDTO.getSeatId().toString())
                        .param("numberOfTickets", String.valueOf(ticketDTO.getNumberOfTickets()))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("ticket/reservation_deleted_successfully"))
                .andExpect(model().attributeExists("ticketId"));

        this.mockMvc.perform(delete("/reservation/" + model().toString())
                        .with(user("client").roles("CLIENT"))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("ticket/reservation_deleted_successfully"));

            Set<Ticket> ticketList = userService.getLoggedInUser().getBoughtTickets();
            assertTrue(ticketList.contains(model().toString()), "Reserved ticket is present in the list after deletion");
    }

    @Test
    void testEditReservation() throws Exception {
        TicketDTO ticketDTO = new TicketDTO(1L, 5L, 1, null); //change the entry data
        int ticketId = 10;
        int newSeatId = 50;
        this.mockMvc.perform(post("/reservation/" + ticketId)
                        .with(user("client").roles("CLIENT"))
                        .param("seatId", String.valueOf(newSeatId))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("ticket/ticket_reservation_edit_successfully"))
                .andExpect(model().attributeExists("ticketId"));

        Seat seat = seatRepository.findById((long) ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Seat with that id was not found"));
        Seat newSeat = seatRepository.findById((long) newSeatId)
                .orElseThrow(() -> new EntityNotFoundException("Seat with that id was not found"));

        assertAll(
                () -> assertEquals(seat.getSeatState(), SeatState.AVAILABLE,
                        "The old seat is expected to be set to AVAILABLE, but its not"),
                () -> assertEquals(newSeat.getSeatState(), SeatState.RESERVED,
                        "The new seat is expected to be set to RESERVED, but its not")
        );
    }

}
