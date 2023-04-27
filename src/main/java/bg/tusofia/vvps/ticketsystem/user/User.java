package bg.tusofia.vvps.ticketsystem.user;

import bg.tusofia.vvps.ticketsystem.ticket.Ticket;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;
    private int age;
    private boolean hasFamily;
    private LocalDate childBirthYear;

    private Role role;

    @OneToMany(mappedBy = "")
    private Set<Ticket> boughtTickets = new HashSet<>();

    public User() {
    }

    public User(String firstName, String lastName, int age, boolean hasFamily, LocalDate childBirthYear) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.hasFamily = hasFamily;
        this.childBirthYear = childBirthYear;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean hasFamily() {
        return hasFamily;
    }

    public void setHasFamily(boolean hasFamily) {
        this.hasFamily = hasFamily;
    }

    public LocalDate getChildBirthYear() {
        return childBirthYear;
    }

    public void setChildBirthYear(LocalDate childBirthYear) {
        this.childBirthYear = childBirthYear;
    }
}
