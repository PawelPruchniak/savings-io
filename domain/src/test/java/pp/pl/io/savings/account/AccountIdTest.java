package pp.pl.io.savings.account;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountIdTest {

  private static final String SOME_ACCOUNT_ID = "SOME_ID";

  @Test
  void shouldThrowIllegalArgumentExceptionWhenAccountIdIsNull() {
    assertThrows(IllegalArgumentException.class, () -> AccountId.of(null));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionWhenAccountIdIsBlank() {
    assertThrows(IllegalArgumentException.class, () -> AccountId.of(""));
  }

  @Test
  void shouldCreateAccountIdSuccessfully() {
    var accountId = AccountId.of(SOME_ACCOUNT_ID);

    assertEquals(SOME_ACCOUNT_ID, accountId.code);
  }

  @Test
  void shouldNotEqualsWhenAccountIdCodeIsDifferent() {
    assertNotEquals(AccountId.of(SOME_ACCOUNT_ID), AccountId.of(SOME_ACCOUNT_ID + "1"));
  }

  @Test
  void shouldEqualsWhenAccountIdCodeMatch() {
    assertEquals(AccountId.of(SOME_ACCOUNT_ID), AccountId.of(SOME_ACCOUNT_ID));
  }

}
