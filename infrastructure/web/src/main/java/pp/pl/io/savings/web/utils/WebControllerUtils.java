package pp.pl.io.savings.web.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import pp.pl.io.savings.domain.exception.Error;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WebControllerUtils {

  public static ResponseStatusException normalizeError(final Throwable throwable) {
    if (throwable instanceof ResponseStatusException responseStatusException) {
      return responseStatusException;
    } else {
      return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, throwable.getMessage(), throwable.getCause());
    }
  }

  public static Exception composeException(final Error error) {
    return switch (error.getCategory()) {
      case UNAUTHORIZED -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, error.getMessage(), error.getCause());
      case NOT_FOUND -> new ResponseStatusException(HttpStatus.NOT_FOUND, error.getMessage(), error.getCause());
      case ILLEGAL_ARGUMENT -> new ResponseStatusException(HttpStatus.BAD_REQUEST, error.getMessage(), error.getCause());
      case CONFLICT -> new ResponseStatusException(HttpStatus.CONFLICT, error.getMessage(), error.getCause());
      default -> new RuntimeException(error.getMessage(), error.getCause());
    };
  }
}
