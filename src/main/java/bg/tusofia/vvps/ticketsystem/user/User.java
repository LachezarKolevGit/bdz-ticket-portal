package bg.tusofia.vvps.ticketsystem.user;

import bg.tusofia.vvps.ticketsystem.ticket.Ticket;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users", schema="public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Email
    private String email;
    @Length(min = 3)
    private String password;
    @Min(5)
    @Max(110)
    private int age;
    private boolean married;
    private LocalDate childBirthYear;
    @NotNull
    private Role role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Ticket> boughtTickets = new HashSet<>();

    public User(String firstName, String lastName, int age, boolean married, LocalDate childBirthYear) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.married = married;
        this.childBirthYear = childBirthYear;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", hasFamily=" + married +
                ", childBirthYear=" + childBirthYear +
                ", role=" + role +
                '}';
    }

    public void addBoughtTicket(Ticket ticket) {
        boughtTickets.add(ticket);
    }

    public void deleteTicket(Ticket ticket){
        boughtTickets.remove(ticket);
    }


}
