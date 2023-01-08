package pp.pl.io.savings.security.core;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
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
  void shouldGetUsernameFromToken() {
    final String jwtToken = jwtTokenManager.generateToken(SOME_USERNAME);
    final String usernameFromToken = jwtTokenManager.getUsernameFromToken(jwtToken);

    assertEquals(SOME_USERNAME, usernameFromToken);
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
