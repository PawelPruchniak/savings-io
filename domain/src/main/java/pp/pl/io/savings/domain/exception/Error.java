package pp.pl.io.savings.domain.exception;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value
@AllArgsConstructor
public class Error {

  @NonNull ErrorCategory category;
  String message;
  Throwable cause;

  public Error(@NonNull ErrorCategory category, String message) {
    this(category, message, null);
  }

  public Error(@NonNull ErrorCategory category, Throwable throwable) {
    this(category, throwable.getMessage(), throwable);
  }

  public enum ErrorCategory {
    NOT_FOUND,
    ILLEGAL_ARGUMENT,
    PROCESSING_ERROR,
    UNAUTHORIZED,
    CONFLICT
  }
}
