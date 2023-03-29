package bg.tusofia.vvps.ticketsystem.ticket;

import java.time.LocalDate;
import java.time.LocalTime;

import bg.tusofia.vvps.ticketsystem.client.User;
import bg.tusofia.vvps.ticketsystem.client.UserService;
import bg.tusofia.vvps.ticketsystem.train.Train;
import bg.tusofia.vvps.ticketsystem.train.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private final UserService userService;
    private LocalTime currentTime;
    private final TrainService trainService;

    @Autowired
    public TicketService(UserService userService, TrainService trainService) {
        this.userService = userService;
        this.trainService = trainService;
        this.currentTime = LocalTime.now();
    }

    public double calculatePrice(Train train, int numberOfTickets, Long trainCarriageId, Long seatId) {
        /**
         * the price will be calculated based on the km of the route and the class of the trainCarriage
         */
        LocalTime morningPeakHourStart = LocalTime.of(7, 30);
        LocalTime morningPeakHourEnd = LocalTime.of(9, 30);

        LocalTime eveningPeakHourStart = LocalTime.of(16, 0);
        LocalTime eveningPeakHourEnd = LocalTime.of(19, 30);

        double basePrice = trainService.calculateBasePrice(train.getId());
        double ticketPrice = basePrice * trainService.getTrainCarriageClass(trainCarriageId);
        trainService.changeSeatStatus(seatId);

        User user = userService.getLoggedInUser();

        if (LocalDate.now().getYear() - user.getChildBirthYear().getYear() < 16) {
            return ticketPrice * numberOfTickets * 50 / 100;
        }

        if (user.getAge() > 60) {
            double priceFirstTicket = (ticketPrice * 34) / 100;
            return priceFirstTicket + (numberOfTickets - 1) * ticketPrice;
        }

        if (user.isHasFamily()) {
            return (numberOfTickets * ticketPrice * 10) / 100;
        }

        if (currentTime.isBefore(morningPeakHourStart) || currentTime.isAfter(morningPeakHourEnd)) {
            double discount = ticketPrice * 0.05;
            return (ticketPrice - discount) * numberOfTickets;
        }

        if (currentTime.isBefore(eveningPeakHourStart) || currentTime.isAfter(eveningPeakHourEnd)) {
            double discount = ticketPrice * 0.05;
            return (ticketPrice - discount) * numberOfTickets;
        }

        return ticketPrice * numberOfTickets;
    }

}
