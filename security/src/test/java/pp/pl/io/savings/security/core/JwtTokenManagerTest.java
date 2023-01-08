package pp.pl.io.savings.security.core;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenManagerTest {

  private static final String SOME_USERNAME = "some-user@gmail.com";

  private final SecretKey testSecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
  private final JwtTokenManager jwtTokenManager = new JwtTokenManager(testSecretKey);

  @Test
  void shouldCreateJwtToken() {
    final String jwtToken = jwtTokenManager.generateToken(SOME_USERNAME);

    assertNotNull(jwtToken);
  }

  @Test
  void shouldNotGetUsernameFromToken() {
    final String jwtToken = jwtTokenManager.generateToken(SOME_USERNAME);
    final Try<String> username = jwtTokenManager.getUsernameFromToken(jwtToken + "invalid");

    assertTrue(username.isFailure());
  }

  @Test
  void shouldGetUsernameFromToken() {
    final String jwtToken = jwtTokenManager.generateToken(SOME_USERNAME);
    final Try<String> username = jwtTokenManager.getUsernameFromToken(jwtToken);

    assertTrue(username.isSuccess());
    assertEquals(SOME_USERNAME, username.get());
  }

  @Test
  void tokenShouldBeValidForRightUsername() {
    final String jwtToken = jwtTokenManager.generateToken(SOME_USERNAME);

    assertTrue(jwtTokenManager.isTokenValid(jwtToken, SOME_USERNAME));
  }

  @Test
  void tokenShouldBeInvalidForWrongUsername() {
    final String jwtToken = jwtTokenManager.generateToken(SOME_USERNAME);

    assertFalse(jwtTokenManager.isTokenValid(jwtToken, SOME_USERNAME + "_"));
  }

  @Test
  void invalidTokenShouldBeInvalid() {
    assertFalse(jwtTokenManager.isTokenValid("fefeaw.344a3gawgaweg.gw43ga", SOME_USERNAME));
  }
}
