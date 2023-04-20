package bg.tusofia.vvps.ticketsystem.client;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String username;
    private int age;
    private boolean hasFamily;
    private LocalDate childBirthYear;

    public Client() {
    }

    public Client(String username, int age, boolean hasFamily, LocalDate childBirthYear) {
        this.username = username;
        this.age = age;
        this.hasFamily = hasFamily;
        this.childBirthYear = childBirthYear;
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
