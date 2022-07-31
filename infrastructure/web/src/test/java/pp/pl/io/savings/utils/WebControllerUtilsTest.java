package pp.pl.io.savings.utils;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import pp.pl.io.savings.exception.Error;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WebControllerUtilsTest {

  private static final Throwable THROWABLE = new Throwable("throwable message");
  private static final Error ERROR_UNAUTHORIZED = new Error(Error.ErrorCategory.UNAUTHORIZED,
      "UNAUTHORIZED ERROR", THROWABLE);
  private static final Error ERROR_NOT_FOUND = new Error(Error.ErrorCategory.NOT_FOUND,
      "NOT_FOUND ERROR", THROWABLE);
  private static final Error ERROR_ILLEGAL_ARGUMENT = new Error(Error.ErrorCategory.ILLEGAL_ARGUMENT,
      "ILLEGAL_ARGUMENT ERROR", THROWABLE);
  private static final Error ERROR_CONFLICT = new Error(Error.ErrorCategory.CONFLICT,
      "CONFLICT ERROR", THROWABLE);
  private static final Error ERROR_PROCESSING = new Error(Error.ErrorCategory.PROCESSING_ERROR,
      "PROCESSING ERROR", THROWABLE);
  private static final RuntimeException RUNTIME_EXCEPTION = new RuntimeException("Runtime message");

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
            RUNTIME_EXCEPTION).toString(),
        exception.toString()
    );
  }
}
