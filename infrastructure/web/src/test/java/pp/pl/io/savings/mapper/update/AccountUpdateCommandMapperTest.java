package pp.pl.io.savings.mapper.update;

import lombok.val;
import org.junit.jupiter.api.Test;
import pp.pl.io.savings.account.AccountType;
import pp.pl.io.savings.account.Currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pp.pl.io.savings.AccountTestData.*;

class AccountUpdateCommandMapperTest {

  @Test
  void shouldThrowWhenAccountUpdateRequestIsNull() {
    assertThrows(NullPointerException.class, () -> AccountUpdateCommandMapper.toAccountUpdateCommand(null));
  }

  @Test
  void shouldMapSavingsAccountRequestCorrectly() {
    val accountCommand = AccountUpdateCommandMapper.toAccountUpdateCommand(SAVINGS_ACCOUNT_UPDATE_REQUEST);

    assertThat(accountCommand)
        .hasFieldOrPropertyWithValue("accountId", UPDATED_SAVINGS_ACCOUNT_ID)
        .hasFieldOrPropertyWithValue("name", "Updated savings account")
        .hasFieldOrPropertyWithValue("description", "Updated savings account description")
        .hasFieldOrPropertyWithValue("currency", Currency.PLN)
        .hasFieldOrPropertyWithValue("balance", 286.66)
        .hasFieldOrPropertyWithValue("accountType", AccountType.SAVINGS);
  }

  @Test
  void shouldMapSavingsAccountMinimumRequestCorrectly() {
    val accountCommand = AccountUpdateCommandMapper.toAccountUpdateCommand(SAVINGS_ACCOUNT_MINIMUM_UPDATE_REQUEST);

    assertThat(accountCommand)
        .hasFieldOrPropertyWithValue("name", "Minimal savings account")
        .hasFieldOrPropertyWithValue("accountId", UPDATED_SAVINGS_ACCOUNT_ID)
        .hasFieldOrPropertyWithValue("currency", Currency.EUR)
        .hasFieldOrPropertyWithValue("balance", 0.0)
        .hasFieldOrPropertyWithValue("accountType", AccountType.SAVINGS);
  }

}
