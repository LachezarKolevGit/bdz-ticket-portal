package bg.tusofia.vvps.ticketsystem.ticket;

import static org.junit.jupiter.api.Assertions.*;

class TicketServiceTest {

    static TicketService ticketService;

    @Mock
    UserService userService;

    @Mock
    TrainService trainService;

    @BeforeAll
    void init(){
         ticketService = new TicketService(userService, trainService);
    }

    @DisplayName("Test .calculatePrice() method")
    @Test
    void testCalculatePrice(){
        Train train = new Train();
        Long trainCarriageId = 1L;
        Long seatId = 1L;
        int numberOfTickets = 1;
        
        when(userService.getLoggedInUser()).thenReturn(new User("Gosho", 50, true, LocalDate.of(1989, 1, 1)));
       double actualPrice = ticketService.calculatePrice(train, numberOfTickets, trainCarriageId, seatId);
       double expectedPrice = ;
       assertEquals(expectedPrice, actualprice, "ExpectedPrice does not match the actualPrice");


    }

}