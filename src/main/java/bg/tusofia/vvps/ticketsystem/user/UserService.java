package bg.tusofia.vvps.ticketsystem.user;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
        user.setRole(Role.CLIENT);

        userRepository.save(user);
    }

    public String tryToAuthenticateUser(UserDTO userDTO) {
        Authentication authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(userDTO.email(), userDTO.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication.getName();
    }

    public User getLoggedInUser() { //rework !!!
        User user = new User("Gosho", "Goshev", 50, true, LocalDate.of(1976, 2, 1));
        return user;
    }

    public void registerAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ADMINISTRATOR);

        userRepository.save(user);
    }
}
