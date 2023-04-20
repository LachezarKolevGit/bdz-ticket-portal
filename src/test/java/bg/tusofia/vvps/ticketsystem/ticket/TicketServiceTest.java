package bg.tusofia.vvps.ticketsystem.ticket;

import bg.tusofia.vvps.ticketsystem.client.Client;
import bg.tusofia.vvps.ticketsystem.client.ClientService;
import bg.tusofia.vvps.ticketsystem.train.Train;
import bg.tusofia.vvps.ticketsystem.train.TrainService;
import bg.tusofia.vvps.ticketsystem.traincarriage.TrainCarriageType;
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

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    public final static double DIESEL_PRICE = 2.80;

    TicketService ticketService;

    @Mock
    ClientService CLientService;

    @Mock
    TrainService trainService;

    @BeforeEach
    void init() {
        ticketService = new TicketService(CLientService, trainService);
    }

    @DisplayName("Test .calculatePrice() method")
    @Test
    void testCalculatePrice() {
        Train train = new Train();
        Long trainCarriageId = 1L;
        Long seatId = 1L;
        int numberOfTickets = 1;

       /* TrainStation sofiaTrainStation = new TrainStation("Sofia Central station", 42.712115, 23.321046);
        TrainStation plovdivTrainStation = new TrainStation("Plovdiv Central railway station", 42.134444, 24.741389);
        TrainStation burgasTrainStation = new TrainStation("Burgas Central railway station", 42.490833, 27.4725);
        List<TrainStation> trainStationList = List.of(sofiaTrainStation, plovdivTrainStation, burgasTrainStation);
*/

       /* Route route = new Route(trainStationList, null);
        //TrainCarriage trainCarriage = new TrainCarriage();
        //Train train = new Train(CarriageType.CLASS_A, );
        RouteService routeService = new RouteService(null);*/

        // when(routeService.calculateRouteDistance(route)).thenReturn(361);
        when(trainService.calculateBasePrice(train.getId())).thenReturn(6.0);
        when(trainService.getTrainCarriageClass(trainCarriageId)).thenReturn(TrainCarriageType.CLASS_B.getMultiplier());
        when(CLientService.getLoggedInUser()).thenReturn(new Client("Gosho", 50, false, null));

        double actualPrice = ticketService.calcFinalPrice(train, numberOfTickets, trainCarriageId, seatId);
        double expectedPrice = 6;
        assertEquals(expectedPrice, actualPrice, "ExpectedPrice does not match the actualPrice");
    }

    @DisplayName("Test .userDiscountPriceHandler() method without meeting the criteria for a discount")
    @Test
    void testUserDiscountPriceHandler() {
        Client client = new Client("Georgi", 50, false, null);
        when(CLientService.getLoggedInUser()).thenReturn(client);
        double actualTicketPrice = ticketService.userDiscountPriceHandler(10);
        double expectedTicketPrice = 10;
        assertEquals(expectedTicketPrice, actualTicketPrice, "Expected ticket price does not match actual ticket price");
    }

    @DisplayName("Test .userDiscountPriceHandler() method for elderly ticket discount")
    @Test
    void testUserDiscountPriceHandlerForElderlyUser() { //34% discount
        Client client = new Client("Georgi", 61, false, null);
        when(CLientService.getLoggedInUser()).thenReturn(client);
        double actualTicketPrice = ticketService.userDiscountPriceHandler(10);
        double expectedTicketPrice = 6.60;
        assertEquals(expectedTicketPrice, actualTicketPrice, "Expected ticket price does not match actual ticket price for elderly user");
    }

    @DisplayName("Test .userDiscountPriceHandler() method for family ticket discount")
    @Test
    void testUserDiscountPriceHandlerForFamilyDiscount() { //10 % discount
        Client client = new Client("Georgi", 45, true, null);
        when(CLientService.getLoggedInUser()).thenReturn(client);
        double actualTicketPrice = ticketService.userDiscountPriceHandler(10);
        double expectedTicketPrice = 9;
        assertEquals(expectedTicketPrice, actualTicketPrice,
                "Expected ticket price does not match actual ticket price with applied" +
                        " family discount with a child above 16");
    }

    @DisplayName("Test .userDiscountPriceHandler() method for having a kid below 16 ticket discount")
    @Test
    void testUserDiscountPriceHandlerForKidBelow16Discount() { //50 % discount for one ticket
        Client client = new Client("Georgi", 45, true, LocalDate.of(2010, 1, 1));
        when(CLientService.getLoggedInUser()).thenReturn(client);
        double actualTicketPrice = ticketService.userDiscountPriceHandler(10);
        double expectedTicketPrice = 10;
        assertEquals(expectedTicketPrice, actualTicketPrice, "Expected ticket price does not match actual ticket price for elderly user");
    }

    private static Stream<Arguments> providePeakHoursRanges(){
        return Stream.of(
                Arguments.of(7, 29),
                Arguments.of(9, 31),
                Arguments.of(15, 59),
                Arguments.of(19, 31)
        );
    }

    @DisplayName("Test .peakHoursDiscountHandler() method for applied discount at a purchase time outside peak hours")
    @ParameterizedTest()
    @MethodSource("providePeakHoursRanges")
    void testPeakHoursDiscountHandlerAfterMorningPeakHour(int hour, int minutes) {
        double actualTicketPrice = ticketService.peakHoursDiscountHandler(10, LocalTime.of(hour,minutes));
        double expectedTicketPrice = 9.50;
        assertEquals(expectedTicketPrice, actualTicketPrice, "Expected ticket price does not match actual ticket price regarding peak hours discount");
    }

}