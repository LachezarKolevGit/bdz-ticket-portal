package bg.tusofia.vvps.ticketsystem.ticket;

import bg.tusofia.vvps.ticketsystem.traincarriage.seat.Seat;
import bg.tusofia.vvps.ticketsystem.traincarriage.seat.SeatRepository;
import bg.tusofia.vvps.ticketsystem.traincarriage.seat.SeatState;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        TicketDTO ticketDTO = new TicketDTO(1L, 5L, 1); //change the entry data
        this.mockMvc.perform(post("/reservation")
                        .with(user("client").roles("CLIENT"))
                        .param("seatId", ticketDTO.getSeatId().toString())
                        .param("numberOfTickets", String.valueOf(ticketDTO.getNumberOfTickets()))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("ticket/ticket_reserved_successfully"))
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

}
