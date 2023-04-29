package bg.tusofia.vvps.ticketsystem.ticket;

public class TicketDTO {
    private Long trainId;
    private Long seatId;
    private int numberOfTickets;

    public TicketDTO() {
    }

    public TicketDTO(Long trainId, Long seatId, int numberOfTickets) {
        this.trainId = trainId;
        this.seatId = seatId;
        this.numberOfTickets = numberOfTickets;
    }

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }
}
