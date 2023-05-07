package bg.tusofia.vvps.ticketsystem.user;

import bg.tusofia.vvps.ticketsystem.security.AuthenticationFacade;
import bg.tusofia.vvps.ticketsystem.ticket.Ticket;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationProvider authenticationProvider;

    public UserService(UserRepository userRepository, AuthenticationProvider authenticationProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationProvider = authenticationProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_CLIENT);

        userRepository.save(user);
    }

    public User getLoggedInUser() {
        Authentication authentication = new AuthenticationFacade().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("Currently logged in user was not found"));
        return user;
    }

    public void registerAdmin(UserDTO userDTO) {
        User admin = new User();
        admin.setEmail(userDTO.getEmail());
        admin.setFirstName(userDTO.getFirstName());
        admin.setLastName(userDTO.getLastName());
        admin.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        admin.setRole(Role.ROLE_ADMINISTRATOR);

        userRepository.save(admin);
    }

    public User findUserById(String userId) {
        return userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new EntityNotFoundException("User was not found"));
    }

    public User editProfile(User redactedUser) {
        User savedUser = userRepository.findById(redactedUser.getId())
                .orElseThrow(() -> new EntityNotFoundException("User was not found"));
        redactedUser.setPassword(savedUser.getPassword());
        userRepository.save(redactedUser);

        return redactedUser;
    }

    public void changePassword() {

    }

    public void addBoughtTicket(Ticket ticket, User user){
        user.addBoughtTicket(ticket);

    }
}
