package pp.pl.io.savings.security.core;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Slf4j
@AllArgsConstructor
public class JwtTokenManager implements Serializable {

  @Serial
  private static final long serialVersionUID = -2550185165626007488L;

  public static final long JWT_TOKEN_VALIDITY = (long) 25 * 60;

  private final Key secretKey;

  public String generateToken(final String username) {
    //todo: maybe refactor to Try<>
    return Jwts
        .builder()
        .setClaims(new HashMap<>())
        .setSubject(username)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
        .signWith(secretKey)
        .compact();
  }

  public boolean isTokenValid(final String token, final String username) {
    try {
      return Jwts.parserBuilder()
          .setSigningKey(secretKey)
          .build()
          .parseClaimsJws(token)
          .getBody()
          .getSubject()
          .equals(username);
    } catch (JwtException e) {
      log.warn("Caught JWT exception {}", e.getMessage());
      return false;
    }
  }

  public String getUsernameFromToken(final String token) {
    //todo: maybe refactor to Try<>
    return Jwts.parserBuilder()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }
}
