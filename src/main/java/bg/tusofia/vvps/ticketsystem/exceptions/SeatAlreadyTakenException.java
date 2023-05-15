package bg.tusofia.vvps.ticketsystem.exceptions;

public class SeatAlreadyTakenException extends RuntimeException{

    public SeatAlreadyTakenException(String message) {
        super(message);
    }
}
