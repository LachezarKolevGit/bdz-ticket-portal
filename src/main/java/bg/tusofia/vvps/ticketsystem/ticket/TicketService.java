package bg.tusofia.vvps.ticketsystem.ticket;

import bg.tusofia.vvps.ticketsystem.client.Client;
import bg.tusofia.vvps.ticketsystem.client.ClientService;
import bg.tusofia.vvps.ticketsystem.train.Train;
import bg.tusofia.vvps.ticketsystem.train.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class TicketService {

    private final ClientService CLientService;
    private final TrainService trainService;

    private static final LocalTime morningPeakHourStart = LocalTime.of(7, 30);
    private static final LocalTime morningPeakHourEnd = LocalTime.of(9, 30);
    private static final LocalTime eveningPeakHourStart = LocalTime.of(16, 0);
    private static final LocalTime eveningPeakHourEnd = LocalTime.of(19, 30);

    @Autowired
    public TicketService(ClientService CLientService, TrainService trainService) {
        this.CLientService = CLientService;
        this.trainService = trainService;
    }

    public double calcFinalPrice(Train train, int numberOfTickets, Long trainCarriageId, Long seatId) {
        /**
         * the price will be calculated based on the km of the route and the class of the trainCarriage
         */

        double basePrice = trainService.calculateBasePrice(train.getId());
        double ticketPrice = basePrice * trainService.getTrainCarriageClass(trainCarriageId);
        double appliedDiscount = userDiscountPriceHandler(ticketPrice);
        if (appliedDiscount != 0) {
            peakHoursDiscountHandler(ticketPrice, LocalTime.now());
        }
        trainService.changeSeatStatus(seatId);
        return ticketPrice * numberOfTickets;
    }

    public double userDiscountPriceHandler(double ticketPrice) {
        Client client = CLientService.getLoggedInUser();
        if (client.getChildBirthYear() != null) { //refactor then nested if statements
            if (LocalDate.now().getYear() - client.getChildBirthYear().getYear() < 16) {
                return ticketPrice * 2 * 50 / 100;
            }
        }
        if (client.getAge() > 60) {
            return ticketPrice - (ticketPrice * 34/100);
        }
        if (client.hasFamily()) {
            return ticketPrice - (ticketPrice * 10/100); //remove the '2'
        }
        return 10;
    }

    public double peakHoursDiscountHandler(double ticketPrice, LocalTime localTime) {
        if (localTime.isBefore(morningPeakHourStart) || localTime.isAfter(morningPeakHourEnd)) {
            double discount = ticketPrice * 0.05;
            return ticketPrice - discount;
        }
        if (localTime.isBefore(eveningPeakHourStart) || localTime.isAfter(eveningPeakHourEnd)) {
            double discount = ticketPrice * 0.05;
            return ticketPrice - discount;
        }
        return ticketPrice;
    }


}
