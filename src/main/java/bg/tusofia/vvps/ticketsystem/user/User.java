package bg.tusofia.vvps.ticketsystem.user;

import bg.tusofia.vvps.ticketsystem.ticket.Ticket;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Collections;
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
    private boolean married;
    private LocalDate childBirthYear;

    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Ticket> boughtTickets = new HashSet<>();

    public User() {
    }

    public User(String firstName, String lastName, int age, boolean married, LocalDate childBirthYear) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.married = married;
        this.childBirthYear = childBirthYear;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean getMarried() {
        return married;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }

    public LocalDate getChildBirthYear() {
        return childBirthYear;
    }

    public void setChildBirthYear(LocalDate childBirthYear) {
        this.childBirthYear = childBirthYear;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Ticket> getBoughtTickets() {
        return Collections.unmodifiableSet(boughtTickets);
    }

    public void setBoughtTickets(Set<Ticket> boughtTickets) {
        this.boughtTickets = boughtTickets;
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
                ", boughtTickets=" + boughtTickets +
                '}';
    }

    public void addBoughtTicket(Ticket ticket) {
        boughtTickets.add(ticket);
    }

    public void deleteTicket(Ticket ticket){
        boughtTickets.remove(ticket);
    }


}
