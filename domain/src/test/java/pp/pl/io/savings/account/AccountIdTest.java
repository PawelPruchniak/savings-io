package pp.pl.io.savings.account;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountIdTest {

  private static final String SOME_ACCOUNT_ID = "123e4567-e89b-42d3-a456-556642440000";
  private static final String SOME_OTHER_ACCOUNT_ID = "123e4567-e89b-42d3-a456-556642440001";
  private static final String SOME_INVALID_ACCOUNT_ID = "SOME_ID";

  @Test
  void shouldThrowIllegalArgumentExceptionWhenAccountIdIsNull() {
    assertThrows(IllegalArgumentException.class, () -> AccountId.of(null));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionWhenAccountIdIsBlank() {
    assertThrows(IllegalArgumentException.class, () -> AccountId.of(""));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionWhenAccountIdIsInvalid() {
    assertThrows(IllegalArgumentException.class, () -> AccountId.of(SOME_INVALID_ACCOUNT_ID));
  }

  @Test
  void shouldCreateAccountIdSuccessfully() {
    var accountId = AccountId.of(SOME_ACCOUNT_ID);

    assertEquals(SOME_ACCOUNT_ID, accountId.code);
  }

  @Test
  void shouldNotEqualsWhenAccountIdCodeIsDifferent() {
    assertNotEquals(AccountId.of(SOME_ACCOUNT_ID), AccountId.of(SOME_OTHER_ACCOUNT_ID));
  }

  @Test
  void shouldEqualsWhenAccountIdCodeMatch() {
    assertEquals(AccountId.of(SOME_ACCOUNT_ID), AccountId.of(SOME_ACCOUNT_ID));
  }

}
