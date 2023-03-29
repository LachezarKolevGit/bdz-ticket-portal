package bg.tusofia.vvps.ticketsystem.client;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getLoggedInUser() { //rework !!!
        User user = new User("Gosho", 50 , true, LocalDate.of(1976,2,1));
        return user;
    }
}
