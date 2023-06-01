package bg.tusofia.vvps.ticketsystem.ticket;

import bg.tusofia.vvps.ticketsystem.train.TrainService;
import bg.tusofia.vvps.ticketsystem.traincarriage.TrainCarriageService;
import bg.tusofia.vvps.ticketsystem.seat.SeatService;
import bg.tusofia.vvps.ticketsystem.user.User;
import bg.tusofia.vvps.ticketsystem.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) //rework
class TicketServiceTest {
    private TicketService ticketService;
    @Mock
    private UserService userService;
    @Mock
    private TrainService trainService;
    @Mock
    private SeatService seatService;
    @Mock
    private TrainCarriageService trainCarriageService;
    @Mock
    private TicketRepository ticketRepository;


    @BeforeEach
    void init() {
        ticketService = new TicketService(userService, trainService, seatService, trainCarriageService, ticketRepository);
    }

    @DisplayName("Test .calculatePrice() method for applying highest discount")
    @Test
    void testCalculatePriceForHighestDiscountCondition() {
        int numberOfTickets = 1;
        when(userService.getLoggedInUser()).thenReturn(new User("Gosho", "Goshev", 65, true, LocalDate.of(2010, 10, 1)));

        double trainBasePrice = 10.0;
        double trainCarriageIdClassMultiplier = 1;
        double actualPrice = ticketService.calculateFinalPrice(trainBasePrice, trainCarriageIdClassMultiplier, numberOfTickets);
        double expectedPrice = 5;
        assertEquals(expectedPrice, actualPrice, "ExpectedPrice does not match the actualPrice");
    }

    @DisplayName("Test .userDiscountPriceHandler() method without meeting the criteria for a discount")
    @Test
    void testUserDiscountPriceHandler() {
        User client = new User("Georgi", "Goshev", 50, false, null);
        when(userService.getLoggedInUser()).thenReturn(client);
        double actualTicketPrice = ticketService.userDiscountPriceHandler(10);
        double expectedTicketPrice = 10;
        assertEquals(expectedTicketPrice, actualTicketPrice, "Expected ticket price does not match actual ticket price");
    }

    @DisplayName("Test .userDiscountPriceHandler() method for elderly ticket discount")
    @Test
    void testUserDiscountPriceHandlerForElderlyUser() { //34% discount
        User client = new User("Georgi", "Goshev", 61, false, null);
        when(userService.getLoggedInUser()).thenReturn(client);
        double actualTicketPrice = ticketService.userDiscountPriceHandler(10);
        double expectedTicketPrice = 6.60;
        assertEquals(expectedTicketPrice, actualTicketPrice, "Expected ticket price does not match actual ticket price for elderly user");
    }

    @DisplayName("Test .userDiscountPriceHandler() method for family ticket discount")
    @Test
    void testUserDiscountPriceHandlerForFamilyDiscount() { //10 % discount
        User client = new User("Georgi", "Goshev", 45, true, null);
        when(userService.getLoggedInUser()).thenReturn(client);
        double actualTicketPrice = ticketService.userDiscountPriceHandler(10);
        double expectedTicketPrice = 9;
        assertEquals(expectedTicketPrice, actualTicketPrice,
                "Expected ticket price does not match actual ticket price with applied" +
                        " family discount with a child above 16");
    }

    @DisplayName("Test .userDiscountPriceHandler() method for having a kid below 16 ticket discount")
    @Test
    void testUserDiscountPriceHandlerForKidBelow16Discount() { //50% discount
        User client = new User("Georgi", "Goshev", 45, true, LocalDate.of(2010, 1, 1));
        when(userService.getLoggedInUser()).thenReturn(client);
        double actualTicketPrice = ticketService.userDiscountPriceHandler(10);
        double expectedTicketPrice = 5;
        assertEquals(expectedTicketPrice, actualTicketPrice, "Expected ticket price does not match actual ticket price for elderly user");
    }

    private static Stream<Arguments> provideOutsidePeakHoursRanges() {
        return Stream.of(
                Arguments.of(7, 29),
                Arguments.of(9, 31),
                Arguments.of(15, 59),
                Arguments.of(19, 31)
        );
    }

    @DisplayName("Test .peakHoursDiscountHandler() method for applied discount at a purchase time outside peak hours")
    @ParameterizedTest()
    @MethodSource("provideOutsidePeakHoursRanges")
    void testPeakHoursDiscountHandlerAfterMorningPeakHour(int hour, int minutes) {
        double actualTicketPrice = ticketService.peakHoursDiscountHandler(10, LocalTime.of(hour, minutes));
        double expectedTicketPrice = 9.50;
        assertEquals(expectedTicketPrice, actualTicketPrice, "Expected ticket price does not match actual ticket price regarding peak hours discount");
    }

    private static Stream<Arguments> providePeakHoursRanges() {
        return Stream.of(
                Arguments.of(7, 31),
                Arguments.of(9, 29),
                Arguments.of(16, 1),
                Arguments.of(19, 29)
        );
    }

    @DisplayName("Test .peakHoursDiscountHandler() method for purchasing a ticket in peak hours")
    @ParameterizedTest
    @MethodSource("providePeakHoursRanges")
    void testPeakHoursTicketPrice(int hour, int minutes) {
        double actualTicketPrice = ticketService.peakHoursDiscountHandler(10, LocalTime.of(hour, minutes));
        double expectedTicketPrice = 10;
        assertEquals(expectedTicketPrice, actualTicketPrice, "Expected ticket price does not match actual ticket price regarding peak hours discount");
    }

}