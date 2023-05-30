package bg.tusofia.vvps.ticketsystem.ticket;

import bg.tusofia.vvps.ticketsystem.traincarriage.seat.Seat;
import bg.tusofia.vvps.ticketsystem.user.User;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(schema="public")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_id_seq")
    private Long id;

    @OneToOne(mappedBy = "ticket", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Seat seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Timestamp purchasedAt;

    private double price;

    @Enumerated  //check if it is correct
    private TicketState ticketState;


    public Ticket() {
    }

    public Ticket(Seat seat, Timestamp purchasedAt, User user) {
        this.seat = seat;
        this.purchasedAt = purchasedAt;
        this.user = user;
    }

    public Ticket(Seat seat, Timestamp purchasedAt, double price, User user) {
        this.seat = seat;
        this.purchasedAt = purchasedAt;
        this.price = price;
        this.user = user;
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

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public void sellTicket(User user, Seat seat) {
        this.user = user;
        user.addBoughtTicket(this);
        this.seat = seat;
        seat.markSeatAsSold(this);
    }

    public void deleteTicket(){
        this.seat.markSeatAsAvailable(this);
        this.user.deleteTicket(this);
    }

}
