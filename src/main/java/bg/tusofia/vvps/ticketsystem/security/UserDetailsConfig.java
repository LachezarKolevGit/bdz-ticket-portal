package bg.tusofia.vvps.ticketsystem.security;

import bg.tusofia.vvps.ticketsystem.user.User;
import bg.tusofia.vvps.ticketsystem.user.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsConfig implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
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
