package pp.pl.io.savings.web.mapper;

import lombok.val;
import org.junit.jupiter.api.Test;
import pp.pl.io.savings.domain.account.asset.Currency;
import pp.pl.io.savings.domain.account.asset.Stocks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pp.pl.io.savings.web.AccountTestData.*;

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
        .hasFieldOrPropertyWithValue("currency", Currency.PLN.getCode())
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
        .hasFieldOrPropertyWithValue("asset", Stocks.GPW.getCode())
        .hasFieldOrPropertyWithValue("assetQuantity", 20.0)
        .hasFieldOrPropertyWithValue("currencyInvested", Currency.PLN.getCode())
        .hasFieldOrPropertyWithValue("amountInvested", 3.99)
        //todo: change this investmentResultValue
        .hasFieldOrPropertyWithValue("investmentResultValue", 0.0)
        .hasFieldOrPropertyWithValue("investmentResultPercentage", 0.0)
        .hasFieldOrPropertyWithValue("accountType", "INVESTMENT");
  }

}
