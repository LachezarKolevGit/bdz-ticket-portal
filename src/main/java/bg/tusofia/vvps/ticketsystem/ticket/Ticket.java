package bg.tusofia.vvps.ticketsystem.ticket;

import bg.tusofia.vvps.ticketsystem.seat.Seat;
import bg.tusofia.vvps.ticketsystem.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(schema="public")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_id_seq")
    @Setter(AccessLevel.NONE)
    private Long id;

    @OneToOne(mappedBy = "ticket", cascade = CascadeType.PERSIST)
    private Seat seat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Timestamp purchasedAt;

    private double price;

    @Enumerated  //check if it is correct
    private TicketState ticketState;

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
