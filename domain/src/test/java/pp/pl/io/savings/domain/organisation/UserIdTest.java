package pp.pl.io.savings.domain.organisation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserIdTest {

  private static final String SOME_USER_ID = "SOME_USER_ID";

  @Test
  void shouldThrowIllegalArgumentExceptionWhenUserIdIsNull() {
    assertThrows(IllegalArgumentException.class, () -> UserId.of(null));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionWhenUserIdIsBlank() {
    assertThrows(IllegalArgumentException.class, () -> UserId.of(""));
  }

  @Test
  void shouldCreateUserIdSuccessfully() {
    var userId = UserId.of(SOME_USER_ID);

    assertEquals(SOME_USER_ID, userId.code);
  }

  @Test
  void shouldNotEqualsWhenUserIdCodeIsDifferent() {
    assertNotEquals(UserId.of(SOME_USER_ID), UserId.of(SOME_USER_ID + "1"));
  }

  @Test
  void shouldEqualsWhenUserIdCodeMatch() {
    assertEquals(UserId.of(SOME_USER_ID), UserId.of(SOME_USER_ID));
  }

}
