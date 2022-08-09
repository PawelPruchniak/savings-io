package pp.pl.io.savings.mapper;

import lombok.val;
import org.junit.jupiter.api.Test;
import pp.pl.io.savings.account.Currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pp.pl.io.savings.AccountTestData.ACCOUNT_ID;
import static pp.pl.io.savings.AccountTestData.SAVINGS_ACCOUNT;

class AccountMapperTest {

  @Test
  void shouldThrowWhenAccountIsNull() {
    assertThrows(NullPointerException.class, () -> AccountMapper.toAccountDTO(null));
  }

  @Test
  void shouldMapSavingsAccountPlnCorrectly() {
    val accountDTO = AccountMapper.toAccountDTO(SAVINGS_ACCOUNT);

    assertThat(accountDTO)
        .hasFieldOrPropertyWithValue("accountId", ACCOUNT_ID)
        .hasFieldOrPropertyWithValue("name", "Savings account")
        .hasFieldOrPropertyWithValue("description", "Some description")
        .hasFieldOrPropertyWithValue("currency", Currency.PLN.name())
        .hasFieldOrPropertyWithValue("accountType", "SAVINGS")
        .hasFieldOrPropertyWithValue("balance", 501.10);
  }

}
