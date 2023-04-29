package bg.tusofia.vvps.ticketsystem.ticket;

import bg.tusofia.vvps.ticketsystem.train.TrainService;
import bg.tusofia.vvps.ticketsystem.traincarriage.TrainCarriageService;
import bg.tusofia.vvps.ticketsystem.traincarriage.seat.SeatService;
import bg.tusofia.vvps.ticketsystem.user.User;
import bg.tusofia.vvps.ticketsystem.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class TicketService {

    private final UserService userService;
    private final TrainService trainService;

    private final SeatService seatService;

    private final TrainCarriageService trainCarriageService;

    private final TicketRepository ticketRepository;

    private static final LocalTime morningPeakHourStart = LocalTime.of(7, 30);
    private static final LocalTime morningPeakHourEnd = LocalTime.of(9, 30);
    private static final LocalTime eveningPeakHourStart = LocalTime.of(16, 0);
    private static final LocalTime eveningPeakHourEnd = LocalTime.of(19, 30);

    @Autowired
    public TicketService(UserService userService, TrainService trainService, SeatService seatService, TrainCarriageService trainCarriageService, TicketRepository ticketRepository) {
        this.userService = userService;
        this.trainService = trainService;
        this.seatService = seatService;
        this.trainCarriageService = trainCarriageService;
        this.ticketRepository = ticketRepository;
    }

    public Long paymentProcess(Long trainId, Long seatId, int numberOfTickets) {
        double trainBasePrice = trainService.calculateBasePrice(trainId);
        double trainCarriageClassMultiplier = trainCarriageService.getTrainCarriageClassPriceMultiplier(seatId);
        double ticketPrice = calculateFinalPrice(trainBasePrice, trainCarriageClassMultiplier, numberOfTickets);

        seatService.markSeatAsSold(seatId);

        Ticket ticket = new Ticket(null, new Timestamp(System.currentTimeMillis()), ticketPrice);
        ticketRepository.save(ticket);
        return ticket.getId();
    }

    /**
     * the price will be calculated based on the km of the route and the class of the trainCarriage
     */
    public double calculateFinalPrice(double trainBasePrice, double trainCarriageClassMultiplier, int numberOfTickets) {
        double ticketPrice = trainBasePrice * trainCarriageClassMultiplier;
        double appliedDiscount = userDiscountPriceHandler(ticketPrice);
        if (appliedDiscount != 0) { //doesnt work
            peakHoursDiscountHandler(ticketPrice, LocalTime.now());
        }

        return ticketPrice * numberOfTickets;
    }

    public double userDiscountPriceHandler(double ticketPrice) {
        User client = userService.getLoggedInUser();
        if (client.getChildBirthYear() != null) { //refactor then nested if statements
            if (LocalDate.now().getYear() - client.getChildBirthYear().getYear() < 16) {
                return ticketPrice * 2 * 50 / 100;
            }
        }
        if (client.getAge() > 60) {
            return ticketPrice - (ticketPrice * 34 / 100);
        }
        if (client.getMarried()) {
            return ticketPrice - (ticketPrice * 10 / 100); //remove the '2'
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
