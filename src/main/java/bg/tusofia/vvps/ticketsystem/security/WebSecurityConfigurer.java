package bg.tusofia.vvps.ticketsystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigurer {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoderConfig passwordEncoderConfig;

    public WebSecurityConfigurer(UserDetailsService userDetailsService, PasswordEncoderConfig passwordEncoderConfig) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoderConfig = passwordEncoderConfig;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoderConfig.getPasswordEncoder());
        authenticationProvider.setUserDetailsService(this.userDetailsService);
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/", "/user/register", "/user/login",
                                        "/user/login/admin").permitAll()
                                .requestMatchers("/user/register/admin", "/user/add",
                                         "/user/add", "/user/edit", "user/getUser/*"
                                ).hasRole("ADMINISTRATOR")
                                .anyRequest().authenticated()
                )
                .formLogin(form -> form.loginPage("/user/login")
                                .loginProcessingUrl("/user/login")
                                .defaultSuccessUrl("/")
                                .failureUrl("/user/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/user/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                        .invalidSessionUrl("/invalidSession.htm")
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true)
                );

        return http.build();
    }
}
