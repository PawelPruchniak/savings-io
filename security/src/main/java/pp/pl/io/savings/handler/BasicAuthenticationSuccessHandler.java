package pp.pl.io.savings.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Configuration
public class BasicAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  private static final String MAIN_PAGE_URL = "/welcome";

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
                                      HttpServletResponse response,
                                      Authentication authentication) throws IOException {
    log.debug("Authentication success");

    if (authentication.getPrincipal() instanceof UserDetails userDetails) {

      var userRoles = Stream.of(userDetails.getAuthorities())
          .map(Object::toString)
          .collect(Collectors.joining(","));

      log.info("User: {} with roles: {} logged in", userDetails.getUsername(), userRoles);
    }

    response.sendRedirect(MAIN_PAGE_URL);
  }
}
