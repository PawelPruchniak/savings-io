package pp.pl.io.savings.mapper;

import lombok.val;
import org.junit.jupiter.api.Test;
import pp.pl.io.savings.account.Currency;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pp.pl.io.savings.AccountTestData.*;

class AccountDtoMapperTest {

  @Test
  void shouldThrowWhenAccountIsNull() {
    assertThrows(NullPointerException.class, () -> AccountDtoMapper.toAccountDTO(null));
  }

  @Test
  void shouldMapSavingsAccountPlnCorrectly() {
    val accountDTO = AccountDtoMapper.toAccountDTO(SAVINGS_ACCOUNT);

    assertThat(accountDTO)
        .hasFieldOrPropertyWithValue("accountId", SAVINGS_ACCOUNT_ID.code)
        .hasFieldOrPropertyWithValue("name", "Savings account")
        .hasFieldOrPropertyWithValue("description", "Some description")
        .hasFieldOrPropertyWithValue("currency", Currency.PLN.name())
        .hasFieldOrPropertyWithValue("balance", 501.10)
        .hasFieldOrPropertyWithValue("accountType", "SAVINGS");
  }

  @Test
  void shouldMapInvestmentAccountGPWCorrectly() {
    val accountDTO = AccountDtoMapper.toAccountDTO(INVESTMENT_ACCOUNT);

    assertThat(accountDTO)
        .hasFieldOrPropertyWithValue("accountId", INVESTMENT_ACCOUNT_ID.code)
        .hasFieldOrPropertyWithValue("name", "Investment account")
        .hasFieldOrPropertyWithValue("description", "Some description")
        .hasFieldOrPropertyWithValue("asset", "GPW")
        .hasFieldOrPropertyWithValue("assetQuantity", 20.0)
        .hasFieldOrPropertyWithValue("currencyInvested", Currency.PLN.name())
        .hasFieldOrPropertyWithValue("amountInvested", 3.99)
        .hasFieldOrPropertyWithValue("investmentResultValue", 0.0)
        .hasFieldOrPropertyWithValue("investmentResultPercentage", 0.0)
        .hasFieldOrPropertyWithValue("accountType", "INVESTMENT");
  }

}
