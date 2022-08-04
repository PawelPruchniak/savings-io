package pp.pl.io.savings.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import pp.pl.io.savings.organisation.SavingsSecurityService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SavingsSecurityServiceImplementationTest {

  private static final SavingsSecurityService savingsSecurityService =
      new SavingsSecurityServiceImplementation();
  private static final UserDetails USER_DETAILS = User.builder()
      .username("some-user-email@gmail.com")
      .authorities("USER_ROLE")
      .build();

  private static final UsernamePasswordAuthenticationToken USER_TOKEN =
      new UsernamePasswordAuthenticationToken(USER_DETAILS, null);

  @Mock
  SecurityContext securityContext;

  @Test
  void shouldReturnUsernameSuccessfully() {
    when(SecurityContextHolder.getContext())
        .thenReturn(securityContext);

    when(securityContext.getAuthentication())
        .thenReturn(USER_TOKEN);

    var username = savingsSecurityService.getUsername();

    assertEquals(USER_DETAILS.getUsername(), username);
  }
}
