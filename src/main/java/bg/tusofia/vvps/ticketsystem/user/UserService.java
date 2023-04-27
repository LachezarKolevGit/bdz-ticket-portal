package bg.tusofia.vvps.ticketsystem.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(User user) {
        userRepository.save(user);
    }

    public ResponseEntity<?> tryToAuthenticateUser(User user) {
        return null;
    }

    public User getLoggedInUser() { //rework !!!
        User user = new User("Gosho", "Goshev", 50, true, LocalDate.of(1976, 2, 1));
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with that email was not found"));
        SimpleGrantedAuthority role = new SimpleGrantedAuthority(user.getRole().name());
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(role);

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), roles);
    }
}
