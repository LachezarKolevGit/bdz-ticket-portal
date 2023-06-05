package bg.tusofia.vvps.ticketsystem.security;

import bg.tusofia.vvps.ticketsystem.user.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
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
    public DefaultSecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        requestCache.setMatchingRequestParameterName(null);

        http
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/home", "/user/register", "/user/login")
                                .permitAll()
                )
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/user/profile", "/user/edit")
                                .authenticated()
                )
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/user/register/admin", "/user/add",
                                "/user/profile/**")
                                .hasRole(Role.ROLE_ADMINISTRATOR.name())
                )
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.POST, "/route")
                        .hasRole(Role.ROLE_ADMINISTRATOR.name())
                )
                .authorizeHttpRequests(request -> request
                        .anyRequest().authenticated()
                )
                .requestCache((cache) -> cache.requestCache(requestCache))
                .formLogin(form -> form.loginPage("/user/login")
                        .loginProcessingUrl("/user/login")
                        .defaultSuccessUrl("/routes", true)
                        .failureUrl("/user/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/user/logout")
                        .permitAll()
                        .logoutSuccessUrl("/user/login"));

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/css/**");
    }
}
