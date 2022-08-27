package pp.pl.io.savings.utils;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pp.pl.io.savings.AccountTestData.*;

class WebControllerUtilsTest {

  @Test
  void shouldComposeUnauthorizedException() {
    var exception = WebControllerUtils.composeException(ERROR_UNAUTHORIZED);

    assertEquals(
        new ResponseStatusException(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED ERROR", THROWABLE).toString(),
        exception.toString()
    );
  }

  @Test
  void shouldComposeNotFoundException() {
    var exception = WebControllerUtils.composeException(ERROR_NOT_FOUND);

    assertEquals(
        new ResponseStatusException(HttpStatus.NOT_FOUND, "NOT_FOUND ERROR", THROWABLE).toString(),
        exception.toString()
    );
  }

  @Test
  void shouldComposeIllegalArgumentException() {
    var exception = WebControllerUtils.composeException(ERROR_ILLEGAL_ARGUMENT);

    assertEquals(
        new ResponseStatusException(HttpStatus.BAD_REQUEST, "ILLEGAL_ARGUMENT ERROR", THROWABLE).toString(),
        exception.toString()
    );
  }

  @Test
  void shouldComposeConflictException() {
    var exception = WebControllerUtils.composeException(ERROR_CONFLICT);

    assertEquals(
        new ResponseStatusException(HttpStatus.CONFLICT, "CONFLICT ERROR", THROWABLE).toString(),
        exception.toString()
    );
  }

  @Test
  void shouldComposeRuntimeException() {
    var exception = WebControllerUtils.composeException(ERROR_PROCESSING);

    assertEquals(
        new RuntimeException(ERROR_PROCESSING.getMessage(), ERROR_PROCESSING.getCause()).toString(),
        exception.toString()
    );
  }

  @Test
  void shouldNormalizeResponseStatusException() {
    var exception = WebControllerUtils.normalizeError(WebControllerUtils.composeException(ERROR_NOT_FOUND));

    assertEquals(
        new ResponseStatusException(HttpStatus.NOT_FOUND, "NOT_FOUND ERROR", THROWABLE).toString(),
        exception.toString()
    );
  }

  @Test
  void shouldNormalizeOtherErrorsToInternalServerException() {
    var exception = WebControllerUtils.normalizeError(RUNTIME_EXCEPTION);

    assertEquals(
        new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, RUNTIME_EXCEPTION.getMessage(),
            RUNTIME_EXCEPTION.getCause()).toString(),
        exception.toString()
    );
  }
}
