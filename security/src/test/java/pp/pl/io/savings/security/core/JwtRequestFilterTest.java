package pp.pl.io.savings.security.core;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtRequestFilterTest {

  private static final String SOME_USERNAME = "some-user@gmail.com";

  private final UserDetailsService userDetailsService = mock(UserDetailsService.class);
  private final JwtTokenManager jwtTokenManager = new JwtTokenManager(Keys.secretKeyFor(SignatureAlgorithm.HS512));
  private final JwtRequestFilter jwtRequestFilter = new JwtRequestFilter(userDetailsService, jwtTokenManager);

  private final HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
  private final HttpServletResponse httpServletResponse = new MockHttpServletResponse();
  private final FilterChain filterChain = new MockFilterChain();

  @BeforeEach
  void clearAuthContext() {
    SecurityContextHolder.setContext(new SecurityContextImpl());
  }

  @Test
  void shouldFilterRequestWithoutAuthHeader() {
    assertDoesNotThrow(() -> jwtRequestFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain));
    assertFalse(userIsAuthenticated());
    verify(userDetailsService, never()).loadUserByUsername(anyString());
  }

  @Test
  void shouldFilterRequestWithAuthHeaderNotStartingWithBearer() {
    final String jwtToken = jwtTokenManager.generateToken(SOME_USERNAME);
    when(httpServletRequest.getHeader("Authorization"))
        .thenReturn(jwtToken);

    assertDoesNotThrow(() -> jwtRequestFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain));
    assertFalse(userIsAuthenticated());
    verify(userDetailsService, never()).loadUserByUsername(anyString());
  }

  @Test
  void shouldFilterRequestWithAuthHeaderAndNotAuthenticateBecauseOfInvalidToken() {
    final String jwtToken = jwtTokenManager.generateToken(SOME_USERNAME);
    when(httpServletRequest.getHeader("Authorization"))
        .thenReturn("Bearer " + jwtToken + "invalid");

    assertDoesNotThrow(() -> jwtRequestFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain));
    assertFalse(userIsAuthenticated());
    verify(userDetailsService, never()).loadUserByUsername(anyString());
  }

  @Test
  void shouldFilterRequestWithAuthHeaderAndNotAuthenticateBecauseAlreadyAuthenticated() {
    final String jwtToken = jwtTokenManager.generateToken(SOME_USERNAME);
    when(httpServletRequest.getHeader("Authorization"))
        .thenReturn("Bearer " + jwtToken);
    final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(SOME_USERNAME, null,
        Collections.emptySet());
    SecurityContextHolder.getContext().setAuthentication(authToken);

    assertDoesNotThrow(() -> jwtRequestFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain));
    assertTrue(userIsAuthenticated());
    verify(userDetailsService, never()).loadUserByUsername(anyString());
  }

  @Test
  void shouldFilterRequestWithAuthHeaderAndNotAuthenticateBecauseOfEmptyUser() {
    final String jwtToken = jwtTokenManager.generateToken(SOME_USERNAME);
    when(httpServletRequest.getHeader("Authorization"))
        .thenReturn("Bearer " + jwtToken);
    when(userDetailsService.loadUserByUsername(SOME_USERNAME))
        .thenReturn(null);

    assertDoesNotThrow(() -> jwtRequestFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain));
    assertFalse(userIsAuthenticated());
    verify(userDetailsService, times(1)).loadUserByUsername(SOME_USERNAME);
  }

  @Test
  void shouldFilterRequestWithAuthHeaderAndAuthenticate() {
    final String jwtToken = jwtTokenManager.generateToken(SOME_USERNAME);
    when(httpServletRequest.getHeader("Authorization"))
        .thenReturn("Bearer " + jwtToken);
    when(userDetailsService.loadUserByUsername(SOME_USERNAME))
        .thenReturn(new User(SOME_USERNAME, "some_password", Collections.emptySet()));

    assertDoesNotThrow(() -> jwtRequestFilter.doFilterInternal(httpServletRequest, httpServletResponse, filterChain));
    assertTrue(userIsAuthenticated());
    verify(userDetailsService, times(1)).loadUserByUsername(SOME_USERNAME);
  }

  private boolean userIsAuthenticated() {
    return SecurityContextHolder.getContext().getAuthentication() != null;
  }
}
