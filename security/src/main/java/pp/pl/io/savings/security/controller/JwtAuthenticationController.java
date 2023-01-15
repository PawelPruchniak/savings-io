package pp.pl.io.savings.security.controller;

import io.vavr.collection.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pp.pl.io.savings.security.core.JwtTokenManager;

import java.io.Serializable;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/security")
@Profile("!integration-tests")
public class JwtAuthenticationController {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenManager jwtTokenManager;
  private final UserDetailsService jwtInMemoryUserDetailsService;

  @GetMapping(value = "/authenticate")
  public ResponseEntity<JwtDTO> createAuthenticationToken(@RequestBody final LoginRequest loginRequest) {
    log.debug("Trying to authenticate user...");

    if (Objects.isNull(loginRequest) || StringUtils.isBlank(loginRequest.username()) || StringUtils.isBlank(loginRequest.password())) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username and password cannot be blank");
    }

    final String secureUsername = loginRequest.username().replaceAll("[\n\r\t]", "_");
    final String securePassword = loginRequest.password().replaceAll("[\n\r\t]", "_");

    authenticate(secureUsername, securePassword);

    final UserDetails userDetails = jwtInMemoryUserDetailsService.loadUserByUsername(secureUsername);
    final String token = jwtTokenManager.generateToken(userDetails.getUsername());

    log.debug("User [" + secureUsername + "] successfully authenticated with Authorities [" +
        List.ofAll(userDetails.getAuthorities()).mkString(", ") + "]");
    return ResponseEntity.ok(new JwtDTO(token));
  }

  private void authenticate(String username, String password) throws DisabledException, BadCredentialsException {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    } catch (DisabledException e) {
      throw new DisabledException("USER_DISABLED", e);
    } catch (BadCredentialsException e) {
      throw new BadCredentialsException("INVALID_CREDENTIALS", e);
    }
  }

  private record JwtDTO(String jwtToken) implements Serializable {
  }

  private record LoginRequest(String username, String password) implements Serializable {
  }
}
