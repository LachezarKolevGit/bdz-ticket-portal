package bg.tusofia.vvps.ticketsystem.ticket;

import bg.tusofia.vvps.ticketsystem.traincarriage.seat.Seat;
import bg.tusofia.vvps.ticketsystem.user.User;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne(mappedBy = "ticket", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Seat seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private Timestamp purchasedAt;
    private double price;

    public Ticket() {
    }

    public Ticket(Seat seat, Timestamp purchasedAt, double price) {
        this.seat = seat;
        this.purchasedAt = purchasedAt;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public Seat getSeat() {
        return seat;
    }

    public User getUser() {
        return user;
    }

    public Timestamp getPurchasedAt() {
        return purchasedAt;
    }

    public Timestamp getReceivedAt() {
        return purchasedAt;
    }

    public void setReceivedAt(Timestamp purchasedAt) {
        this.purchasedAt = purchasedAt;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
