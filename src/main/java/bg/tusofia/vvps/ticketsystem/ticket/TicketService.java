package bg.tusofia.vvps.ticketsystem.ticket;

import bg.tusofia.vvps.ticketsystem.exceptions.SeatAlreadyTakenException;
import bg.tusofia.vvps.ticketsystem.seat.Seat;
import bg.tusofia.vvps.ticketsystem.seat.SeatService;
import bg.tusofia.vvps.ticketsystem.seat.SeatState;
import bg.tusofia.vvps.ticketsystem.train.Train;
import bg.tusofia.vvps.ticketsystem.train.TrainService;
import bg.tusofia.vvps.ticketsystem.traincarriage.TrainCarriageService;
import bg.tusofia.vvps.ticketsystem.user.User;
import bg.tusofia.vvps.ticketsystem.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.PermissionDeniedDataAccessException;
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

    private static final LocalTime MORNING_PEAK_HOUR_START = LocalTime.of(7, 30, 0, 0);
    private static final LocalTime MORNING_PEAK_HOUR_END = LocalTime.of(9, 30, 0, 0);
    private static final LocalTime EVENING_PEAK_HOUR_START = LocalTime.of(16, 0, 0, 0);
    private static final LocalTime EVENING_PEAK_HOUR_END = LocalTime.of(19, 30, 0, 0);

    public TicketService(UserService userService, TrainService trainService, SeatService seatService, TrainCarriageService trainCarriageService, TicketRepository ticketRepository) {
        this.userService = userService;
        this.trainService = trainService;
        this.seatService = seatService;
        this.trainCarriageService = trainCarriageService;
        this.ticketRepository = ticketRepository;
    }

    public Long paymentProcess(TicketDTO ticketDTO) {
        Train train = seatService.findTrainBySeatId(ticketDTO.getSeatId());
        //every db action must be in a transaction
        double trainBasePrice = trainService.calculateBasePrice(train.getId());
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
        if (appliedDiscount == ticketPrice) {
            return peakHoursDiscountHandler(ticketPrice, LocalTime.now());
        }

        return appliedDiscount * numberOfTickets;
    }

    public double userDiscountPriceHandler(double ticketPrice) {
        User user = userService.getLoggedInUser();
        if (user.getChildBirthYear() != null) {
            if ((LocalDate.now().getYear() - user.getChildBirthYear().getYear()) < 16) {
                return ticketPrice * 50 / 100;
            }
        }
        if (user.getAge() > 60) {
            return ticketPrice - (ticketPrice * 34 / 100);
        }
        if (user.isMarried()) {
            return ticketPrice - (ticketPrice * 10 / 100); //remove the '2'
        }
        return ticketPrice; //probably has to rework
    }

    public double peakHoursDiscountHandler(double ticketPrice, LocalTime localTime) {
        if (localTime.isAfter(MORNING_PEAK_HOUR_START) && localTime.isBefore(MORNING_PEAK_HOUR_END)) {
            return ticketPrice;
        }
        if (localTime.isAfter(EVENING_PEAK_HOUR_START) && localTime.isBefore(EVENING_PEAK_HOUR_END)) {
            return ticketPrice;
        }

        double discount = ticketPrice * 0.05;
        return ticketPrice - discount;
    }

    public Long reserveTicket(TicketDTO ticketDTO) {
        Seat seat = seatService.getSeatById(ticketDTO.getSeatId());
        if (seat.getSeatState() .equals(SeatState.AVAILABLE)) {
            seatService.markSeatAsReserved(seat.getId());
            Ticket ticket = new Ticket(seat,
                    new Timestamp(System.currentTimeMillis()), ticketDTO.getBuyer());
            ticketRepository.save(ticket);
            return ticket.getId();
        } else {
            throw new SeatAlreadyTakenException("Seat is not available for reservation");
        }
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
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Ticket was not found"));

        if (!ticket.getUser().equals(userService.getLoggedInUser())) {
            throw new PermissionDeniedDataAccessException("You can't delete a ticket which is not bought by you", new Throwable());
        }
        ticket.deleteTicket();
        ticketRepository.deleteById(ticketId);
    }
}
