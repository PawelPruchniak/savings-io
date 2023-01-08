package pp.pl.io.savings.security.core;

import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

  private final UserDetailsService userDetailsService;
  private final JwtTokenManager jwtTokenManager;

  @Override
  protected void doFilterInternal(final HttpServletRequest request,
                                  @NonNull final HttpServletResponse response,
                                  @NonNull final FilterChain chain) throws ServletException, IOException {
    final String requestTokenHeader = request.getHeader("Authorization");

    if (StringUtils.isNotBlank(requestTokenHeader)) {
      if (requestTokenHeader.startsWith("Bearer ")) {
        final String jwtToken = requestTokenHeader.substring(7);
        authorizeUserWithToken(request, jwtToken);
      } else {
        log.warn("JWT Token does not begin with Bearer String");
      }
    }

    chain.doFilter(request, response);
  }

  private void authorizeUserWithToken(final HttpServletRequest request,
                                      final String jwtToken) {
    final Try<String> username = getUsernameFromToken(jwtToken);

    if (username.isSuccess() && userIsNotAuthenticated()) {

      final UserDetails userDetails = userDetailsService.loadUserByUsername(username.get());

      if (userDetails != null && jwtTokenManager.isTokenValid(jwtToken, userDetails.getUsername())) {
        final UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder
            .getContext()
            .setAuthentication(authToken);
        log.debug("User [" + userDetails.getUsername() + "] successfully authenticated via JWT");
      } else {
        log.warn("Token is not valid");
      }
    }
  }

  private static boolean userIsNotAuthenticated() {
    return SecurityContextHolder.getContext().getAuthentication() == null;
  }

  private Try<String> getUsernameFromToken(final String jwtToken) {
    return jwtTokenManager.getUsernameFromToken(jwtToken);
  }

}
