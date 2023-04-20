package bg.tusofia.vvps.ticketsystem.ticket;

import java.sql.Timestamp;
public class Ticket {

    private Long id;

    private Long trainCarriageId;

    private Long seatId;
    private Timestamp receivedAt;
    private float price;

    public Ticket() {
    }

    public Ticket(Long trainCarriageId, Long seatId, Timestamp receivedAt, float price) {
        this.trainCarriageId = trainCarriageId;
        this.seatId = seatId;
        this.receivedAt = receivedAt;
        this.price = price;
    }

    public Long getTrainCarriageId() {
        return trainCarriageId;
    }

    public void setTrainCarriageId(Long trainCarriageId) {
        this.trainCarriageId = trainCarriageId;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public Timestamp getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(Timestamp receivedAt) {
        this.receivedAt = receivedAt;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
