package pp.pl.io.savings.core;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

  @Serial
  private static final long serialVersionUID = -7858869558953243875L;

  @Override
  public void commence(final HttpServletRequest aRequest, final HttpServletResponse aResponse,
                       final AuthenticationException aAuthException) throws IOException {

    aResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
  }
}
