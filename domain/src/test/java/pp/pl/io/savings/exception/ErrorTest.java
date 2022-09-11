package pp.pl.io.savings.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorTest {

  private static final String SOME_MESSAGE = "some message";
  public static final RuntimeException SOME_ERROR = new RuntimeException("Some error");

  @Test
  void shouldThrowNullPointerExceptionWhenErrorCategoryIsNull() {
    assertThrows(NullPointerException.class, () -> new Error(null, SOME_MESSAGE));
    assertThrows(NullPointerException.class, () -> new Error(null, SOME_MESSAGE, SOME_ERROR));
  }

  @Test
  void shouldCreateErrorWithOnlyMessageSuccessfully() {
    var error = new Error(Error.ErrorCategory.PROCESSING_ERROR, SOME_MESSAGE);

    assertEquals(Error.ErrorCategory.PROCESSING_ERROR, error.getCategory());
    assertEquals(SOME_MESSAGE, error.getMessage());
    assertNull(error.getCause());
  }

  @Test
  void shouldCreateErrorWithOnlyThrowableSuccessfully() {
    var error = new Error(Error.ErrorCategory.PROCESSING_ERROR, SOME_ERROR);

    assertEquals(Error.ErrorCategory.PROCESSING_ERROR, error.getCategory());
    assertEquals(SOME_ERROR.getMessage(), error.getMessage());
    assertEquals(new Throwable(SOME_ERROR).getCause(), error.getCause());
  }

  @Test
  void shouldCreateErrorSuccessfully() {
    var error = new Error(Error.ErrorCategory.PROCESSING_ERROR, SOME_MESSAGE, SOME_ERROR);

    assertEquals(Error.ErrorCategory.PROCESSING_ERROR, error.getCategory());
    assertEquals(SOME_MESSAGE, error.getMessage());
    assertEquals(new Throwable(SOME_ERROR).getCause(), error.getCause());
  }

}
