package pp.pl.io.savings.security.controller;

import io.jsonwebtoken.security.InvalidKeyException;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.server.ResponseStatusException;
import pp.pl.io.savings.security.core.JwtTokenManager;

import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationControllerTest {

  @Mock
  private AuthenticationManager authenticationManager;
  @Mock
  private UserDetailsService userDetailsService;
  @Mock
  private JwtTokenManager jwtTokenManager;

  @InjectMocks
  private JwtAuthenticationController jwtAuthenticationController;


  private static final JwtAuthenticationController.LoginRequest LOGIN_REQUEST = new JwtAuthenticationController.LoginRequest("login",
      "password");
  private static final UsernamePasswordAuthenticationToken AUTHENTICATION_TOKEN =
      new UsernamePasswordAuthenticationToken(LOGIN_REQUEST.username(), LOGIN_REQUEST.password());
  private static final UserDetails USER_DETAILS = new User(LOGIN_REQUEST.username(), LOGIN_REQUEST.password(), Collections.emptySet());

  private static Stream<JwtAuthenticationController.LoginRequest> invalidLoginRequest() {
    return Stream.of(
        null,
        new JwtAuthenticationController.LoginRequest(null, "password"),
        new JwtAuthenticationController.LoginRequest("", "password"),
        new JwtAuthenticationController.LoginRequest("login", null),
        new JwtAuthenticationController.LoginRequest("login", "")
    );
  }

  @ParameterizedTest
  @MethodSource("invalidLoginRequest")
  void shouldThrowIfLoginRequestIsInvalid(final JwtAuthenticationController.LoginRequest loginRequest) {
    val exception = assertThrows(ResponseStatusException.class, () -> jwtAuthenticationController.createAuthenticationToken(loginRequest));
    assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
    assertEquals("Username and password cannot be blank", exception.getReason());
  }

  @Test
  void shouldThrowIfUserIsDisable() {
    when(authenticationManager.authenticate(AUTHENTICATION_TOKEN))
        .thenThrow(new DisabledException("User is disabled"));

    val exception = assertThrows(DisabledException.class, () -> jwtAuthenticationController.createAuthenticationToken(LOGIN_REQUEST));
    assertEquals("USER_DISABLED", exception.getMessage());
  }

  @Test
  void shouldThrowIfLoginRequestHasInvalidCredentials() {
    when(authenticationManager.authenticate(AUTHENTICATION_TOKEN))
        .thenThrow(new BadCredentialsException("Bad credentials"));

    val exception = assertThrows(BadCredentialsException.class, () -> jwtAuthenticationController.createAuthenticationToken(LOGIN_REQUEST));
    assertEquals("INVALID_CREDENTIALS", exception.getMessage());
  }

  @Test
  void shouldThrowIfAuthenticateThrowsOtherAuthenticationException() {
    final UsernameNotFoundException usernameNotFoundException = new UsernameNotFoundException("Username not found");
    when(authenticationManager.authenticate(AUTHENTICATION_TOKEN))
        .thenThrow(usernameNotFoundException);

    val exception = assertThrows(UsernameNotFoundException.class,
        () -> jwtAuthenticationController.createAuthenticationToken(LOGIN_REQUEST));
    assertEquals(usernameNotFoundException, exception);
  }

  @Test
  void shouldThrowIfCannotLoadUserByUsername() {
    when(authenticationManager.authenticate(AUTHENTICATION_TOKEN))
        .thenReturn(AUTHENTICATION_TOKEN);
    final UsernameNotFoundException usernameNotFoundException = new UsernameNotFoundException("Username not found");
    when(userDetailsService.loadUserByUsername(LOGIN_REQUEST.username()))
        .thenThrow(usernameNotFoundException);

    val exception = assertThrows(UsernameNotFoundException.class,
        () -> jwtAuthenticationController.createAuthenticationToken(LOGIN_REQUEST));
    assertEquals(usernameNotFoundException, exception);
  }

  @Test
  void shouldThrowIfCannotGenerateToken() {
    when(authenticationManager.authenticate(AUTHENTICATION_TOKEN))
        .thenReturn(AUTHENTICATION_TOKEN);
    when(userDetailsService.loadUserByUsername(LOGIN_REQUEST.username()))
        .thenReturn(USER_DETAILS);
    final InvalidKeyException invalidKeyException = new InvalidKeyException("Invalid key");
    when(jwtTokenManager.generateToken(LOGIN_REQUEST.username()))
        .thenThrow(invalidKeyException);

    val exception = assertThrows(InvalidKeyException.class,
        () -> jwtAuthenticationController.createAuthenticationToken(LOGIN_REQUEST));
    assertEquals(invalidKeyException, exception);
  }

  @Test
  void shouldReturnJwtTokenSuccessfully() {
    when(authenticationManager.authenticate(AUTHENTICATION_TOKEN))
        .thenReturn(AUTHENTICATION_TOKEN);
    when(userDetailsService.loadUserByUsername(LOGIN_REQUEST.username()))
        .thenReturn(USER_DETAILS);
    final String jwtToken = "eyJhbGciOiJIUzI1NiJ9" +
        ".eyJqdGkiOiJlNjc4ZjIzMzQ3ZTM0MTBkYjdlNjg3Njc4MjNiMmQ3MCIsImlhdCI6MTQ2NjYzMzMxNywibmJmIjoxNDY2NjMzMzE3LCJleHAiOjE0NjY2MzY5MTd9" +
        ".rgx_o8VQGuDa2AqCHSgVOD5G68Ld_YYM7N7THmvLIKc";
    when(jwtTokenManager.generateToken(LOGIN_REQUEST.username()))
        .thenReturn(jwtToken);

    val result = jwtAuthenticationController.createAuthenticationToken(LOGIN_REQUEST);
    val expectedResult = ResponseEntity.ok(new JwtAuthenticationController.JwtDTO(jwtToken, JwtTokenManager.JWT_TOKEN_VALIDITY));

    assertEquals(expectedResult, result);
  }

}
