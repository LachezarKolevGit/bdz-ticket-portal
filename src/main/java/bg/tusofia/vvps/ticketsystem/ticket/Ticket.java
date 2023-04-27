package bg.tusofia.vvps.ticketsystem.ticket;

import bg.tusofia.vvps.ticketsystem.traincarriage.seat.Seat;
import jakarta.persistence.*;

import java.sql.Timestamp;
@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne //research more
    private Seat seat;
    private Timestamp purchasedAt;
    private float price;

    public Ticket() {
    }

    public Ticket(Seat seat, Timestamp purchasedAt, float price) {
        this.seat = seat;
        this.purchasedAt = purchasedAt;
        this.price = price;
    }


    public Timestamp getReceivedAt() {
        return purchasedAt;
    }

    public void setReceivedAt(Timestamp purchasedAt) {
        this.purchasedAt = purchasedAt;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
