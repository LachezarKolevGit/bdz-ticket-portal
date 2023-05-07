package bg.tusofia.vvps.ticketsystem.ticket;

import bg.tusofia.vvps.ticketsystem.train.TrainService;
import bg.tusofia.vvps.ticketsystem.traincarriage.TrainCarriageService;
import bg.tusofia.vvps.ticketsystem.traincarriage.seat.Seat;
import bg.tusofia.vvps.ticketsystem.traincarriage.seat.SeatService;
import bg.tusofia.vvps.ticketsystem.user.User;
import bg.tusofia.vvps.ticketsystem.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public Long paymentProcess(TicketDTO ticketDTO) {
        //every db action must be in a transaction
        double trainBasePrice = trainService.calculateBasePrice(ticketDTO.getTrainId());
        double trainCarriageClassMultiplier = trainCarriageService.getTrainCarriageClassPriceMultiplier(ticketDTO.getSeatId());
        double ticketPrice = calculateFinalPrice(trainBasePrice, trainCarriageClassMultiplier, ticketDTO.getNumberOfTickets());

        Seat seat = seatService.markSeatAsSold(ticketDTO.getSeatId());
        Ticket ticket = new Ticket(seat,
                new Timestamp(System.currentTimeMillis()), ticketPrice, ticketDTO.getBuyer());
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
        User user = userService.getLoggedInUser();

        if (user.getChildBirthYear() != null) { //refactor then nested if statements
            if (LocalDate.now().getYear() - user.getChildBirthYear().getYear() < 16) {
                return ticketPrice * 2 * 50 / 100;
            }
        }
        if (user.getAge() > 60) {
            return ticketPrice - (ticketPrice * 34 / 100);
        }
        if (user.getMarried()) {
            return ticketPrice - (ticketPrice * 10 / 100); //remove the '2'
        }
        return ticketPrice; //probably has to rework
    }

    public double peakHoursDiscountHandler(double ticketPrice, LocalTime localTime) {
        if (localTime.isAfter(morningPeakHourStart) && localTime.isBefore(morningPeakHourEnd)){
            return ticketPrice;
        }
        if (localTime.isAfter(eveningPeakHourStart) && localTime.isBefore(eveningPeakHourEnd)){
            return ticketPrice;
        }

        double discount = ticketPrice * 0.05;
        return ticketPrice - discount;
    }

    public Long reserveTicket(TicketDTO ticketDTO) {
        Seat seat = seatService.markSeatAsReserved(ticketDTO.getSeatId());
        Ticket ticket = new Ticket(seat,
                new Timestamp(System.currentTimeMillis()), ticketDTO.getBuyer());
        ticketRepository.save(ticket);
        return ticket.getId();
    }

    public Page<Ticket> getAllReservations(int page) {
        int pageSize = 10;
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        User currentlyLoggedInUser = userService.getLoggedInUser();
        return ticketRepository.findAllByTicketStateAndUser
                (TicketState.RESERVED, currentlyLoggedInUser, pageRequest);

    }

    public Long editReservation(Long ticketId, Long newSeatId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Ticket was not found"));
        Seat oldSeat = ticket.getSeat();
        oldSeat.markSeatAsAvailable(ticket);
        Seat seat = seatService.markSeatAsReserved(newSeatId);
        ticket.setSeat(seat);

        ticketRepository.save(ticket);
        return ticket.getId();
    }

    public void deleteReservation(Long ticketId) {
        ticketRepository.deleteById(ticketId);
    }
}
