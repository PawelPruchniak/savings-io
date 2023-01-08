package pp.pl.io.savings.security.core;

import io.vavr.collection.HashSet;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import pp.pl.io.savings.domain.organisation.UserId;
import pp.pl.io.savings.domain.organisation.UserRepository;
import pp.pl.io.savings.domain.organisation.UserRole;
import pp.pl.io.savings.domain.organisation.UserWithRoles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SavingsSecurityServiceTest {

  @Mock
  UserRepository userRepository;

  @InjectMocks
  private SavingsSecurityService savingsSecurityService;

  private static final UserDetails USER_DETAILS = User.builder()
      .username("some-user-email@gmail.com")
      .password("some-password")
      .authorities("ROLE_1", "ROLE_2")
      .build();

  private static final UsernamePasswordAuthenticationToken USER_TOKEN =
      new UsernamePasswordAuthenticationToken(USER_DETAILS, null);

  protected static final RuntimeException SOME_PROCESSING_ERROR = new RuntimeException("Some processing error");

  @Mock
  SecurityContext securityContext;

  @Test
  void shouldReturnNullUsernameWhenMissingAuthentication() {
    SecurityContextHolder.setContext(securityContext);
    when(securityContext.getAuthentication())
        .thenReturn(null);

    var username = savingsSecurityService.getUsername();

    assertNull(username);
  }

  @Test
  void shouldReturnUsernameSuccessfully() {
    SecurityContextHolder.setContext(securityContext);
    when(securityContext.getAuthentication())
        .thenReturn(USER_TOKEN);

    var username = savingsSecurityService.getUsername();

    assertEquals(USER_DETAILS.getUsername(), username);
  }

  @Test
  void shouldReturnEmptyRolesWhenMissingAuthentication() {
    SecurityContextHolder.setContext(securityContext);
    when(securityContext.getAuthentication())
        .thenReturn(null);

    var roles = savingsSecurityService.getUserRoles();

    assertEquals(HashSet.empty(), roles);
  }

  @Test
  void shouldReturnRolesSuccessfully() {
    SecurityContextHolder.setContext(securityContext);
    when(securityContext.getAuthentication())
        .thenReturn(USER_TOKEN);

    var roles = savingsSecurityService.getUserRoles();

    assertEquals(
        HashSet.of(UserRole.of("ROLE_1"), UserRole.of("ROLE_2")),
        roles
    );
  }

  @Test
  void shouldReturnUserWithRolesSuccessfully() {
    SecurityContextHolder.setContext(securityContext);
    when(securityContext.getAuthentication())
        .thenReturn(USER_TOKEN);

    var userWithRoles = savingsSecurityService.getUserWithRoles();

    assertEquals(
        UserWithRoles.builder()
            .username(USER_DETAILS.getUsername())
            .roles(HashSet.of(UserRole.of("ROLE_1"), UserRole.of("ROLE_2")))
            .build(),
        userWithRoles
    );
  }

  @Test
  void shouldReturnNullUserIdWhenUsernameIsNull() {
    SecurityContextHolder.setContext(securityContext);
    when(securityContext.getAuthentication())
        .thenReturn(null);

    assertNull(savingsSecurityService.getUserId());
  }

  @Test
  void shouldReturnNullUserIdWhenFetchUserIdIsFailure() {
    SecurityContextHolder.setContext(securityContext);
    when(securityContext.getAuthentication())
        .thenReturn(USER_TOKEN);
    when(userRepository.getUserId(any()))
        .thenReturn(Try.failure(SOME_PROCESSING_ERROR));

    assertNull(savingsSecurityService.getUserId());
  }

  @Test
  void shouldReturnNullUserIdWhenFetchUserIdIsEmpty() {
    SecurityContextHolder.setContext(securityContext);
    when(securityContext.getAuthentication())
        .thenReturn(USER_TOKEN);
    when(userRepository.getUserId(any()))
        .thenReturn(Try.of(Option::none));

    assertNull(savingsSecurityService.getUserId());
  }

  @Test
  void shouldReturnNullUserIdWhenFetchUserIdIsBlank() {
    SecurityContextHolder.setContext(securityContext);
    when(securityContext.getAuthentication())
        .thenReturn(USER_TOKEN);
    when(userRepository.getUserId(any()))
        .thenReturn(Try.of(() -> Option.of("")));

    assertNull(savingsSecurityService.getUserId());
  }

  @Test
  void shouldReturnUserIdSuccessfully() {
    SecurityContextHolder.setContext(securityContext);
    when(securityContext.getAuthentication())
        .thenReturn(USER_TOKEN);
    when(userRepository.getUserId(any()))
        .thenReturn(Try.of(() -> Option.of("USER_1")));

    assertEquals(UserId.of("USER_1"), savingsSecurityService.getUserId());
  }
}
