package bg.tusofia.vvps.ticketsystem.user;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
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

  /*  public String tryToAuthenticateUser(UserDTO userDTO) {
        Authentication authentication = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(userDTO.email(), userDTO.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication.getName();
    }*/

    public User getLoggedInUser() { //rework !!!
        User user = new User("Gosho", "Goshev", 50, true, LocalDate.of(1976, 2, 1));
        return user;
    }

    public void registerAdmin(UserDTO userDTO) {
        User admin = new User();
        admin.setEmail(userDTO.getEmail());
        admin.setFirstName(userDTO.getFirstName());
        admin.setLastName(userDTO.getLastName());
        admin.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        admin.setRole(Role.ADMINISTRATOR);

        userRepository.save(admin);
    }

    public User findUserById(String userId) {
        return userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new EntityNotFoundException("User was not found"));
    }

    public User editProfile(User redactedUser) {
       /*User oldUserProfile = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new EntityNotFoundException("User with that id was not found"));*//*
        oldUserProfile.setEmail(user.getEmail());
        oldUserProfile.setFirstName(user.getFirstName());
        oldUserProfile.setLastName(user.getLastName());
        oldUserProfile.setAge(user.getAge());
        oldUserProfile.setMarried(user.getMarried());
        oldUserProfile.setChildBirthYear(user.getChildBirthYear());*/

        userRepository.save(redactedUser);

        return redactedUser;
    }
}
