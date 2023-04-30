package bg.tusofia.vvps.ticketsystem.security;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();
}
