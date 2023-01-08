package pp.pl.io.savings.web.mapper.create;

import lombok.val;
import org.junit.jupiter.api.Test;
import pp.pl.io.savings.domain.account.AccountType;
import pp.pl.io.savings.domain.account.asset.Currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pp.pl.io.savings.web.AccountTestData.SAVINGS_ACCOUNT_MINIMUM_REQUEST;
import static pp.pl.io.savings.web.AccountTestData.SAVINGS_ACCOUNT_REQUEST;

class AccountCommandMapperTest {

  @Test
  void shouldThrowWhenAccountRequestIsNull() {
    assertThrows(NullPointerException.class, () -> AccountCommandMapper.toAccountCommand(null));
  }

  @Test
  void shouldMapSavingsAccountRequestCorrectly() {
    val accountCommand = AccountCommandMapper.toAccountCommand(SAVINGS_ACCOUNT_REQUEST);

    assertThat(accountCommand)
        .hasFieldOrPropertyWithValue("name", "Savings account")
        .hasFieldOrPropertyWithValue("description", "Savings account description")
        .hasFieldOrPropertyWithValue("currency", Currency.EUR)
        .hasFieldOrPropertyWithValue("balance", 500.76)
        .hasFieldOrPropertyWithValue("accountType", AccountType.SAVINGS);
  }

  @Test
  void shouldMapSavingsAccountMinimumRequestCorrectly() {
    val accountCommand = AccountCommandMapper.toAccountCommand(SAVINGS_ACCOUNT_MINIMUM_REQUEST);

    assertThat(accountCommand)
        .hasFieldOrPropertyWithValue("name", "Minimal savings account")
        .hasFieldOrPropertyWithValue("currency", Currency.USD)
        .hasFieldOrPropertyWithValue("balance", 0.0)
        .hasFieldOrPropertyWithValue("accountType", AccountType.SAVINGS);
  }

}
