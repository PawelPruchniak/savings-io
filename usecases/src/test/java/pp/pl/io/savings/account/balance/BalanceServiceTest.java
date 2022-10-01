package pp.pl.io.savings.account.balance;

import io.vavr.collection.List;
import org.junit.jupiter.api.Test;
import pp.pl.io.savings.account.Currency;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pp.pl.io.savings.ServiceTestData.*;
import static pp.pl.io.savings.account.balance.ExchangeRateTestData.*;

class BalanceServiceTest {

  private final BalanceService balanceService =
      new BalanceService(
          new CurrencyService(
              new TestExchangeRatesStructure(EXCHANGE_PAIR_VALUE_MAP)
          )
      );

  @Test
  void shouldCalculatePLNBalanceWithOnePLNSavingsAccount() {
    assertEquals(
        SAVINGS_ACCOUNT_PLN_VALUE,
        balanceService.calculateTotalBalance(List.of(SAVINGS_ACCOUNT_PLN), Currency.PLN)
    );
  }

  @Test
  void shouldCalculatePLNBalanceWithOneUSDSavingsAccount() {
    assertEquals(
        SAVINGS_ACCOUNT_USD_VALUE.multiply(BigDecimal.valueOf(USD_PLN_VALUE)),
        balanceService.calculateTotalBalance(List.of(SAVINGS_ACCOUNT_USD), Currency.PLN)
    );
  }

  @Test
  void shouldCalculateUSDBalanceWithOneUSDSavingsAccount() {
    assertEquals(
        SAVINGS_ACCOUNT_USD_VALUE,
        balanceService.calculateTotalBalance(List.of(SAVINGS_ACCOUNT_USD), Currency.USD)
    );
  }

  @Test
  void shouldCalculateUSDBalanceWithOnePLNSavingsAccount() {
    assertEquals(
        SAVINGS_ACCOUNT_PLN_VALUE.multiply(BigDecimal.valueOf(PLN_USD_VALUE)),
        balanceService.calculateTotalBalance(List.of(SAVINGS_ACCOUNT_PLN), Currency.USD)
    );
  }

  @Test
  void shouldCalculatePLNBalanceWithFewSavingsAccounts() {
    assertEquals(
        SAVINGS_ACCOUNT_PLN_VALUE
            .add(SAVINGS_ACCOUNT_USD_VALUE.multiply(BigDecimal.valueOf(USD_PLN_VALUE))),
        balanceService.calculateTotalBalance(List.of(SAVINGS_ACCOUNT_PLN, SAVINGS_ACCOUNT_USD), Currency.PLN)
    );
  }

  @Test
  void shouldCalculatePLNBalanceWithOneStocksInvestmentAccount() {
    assertEquals(
        BigDecimal.ZERO,
        balanceService.calculateTotalBalance(List.of(INVESTMENT_ACCOUNT_STOCKS), Currency.PLN)
    );
  }

  //todo: Add tests for Investment Account

  @Test
  void shouldCalculatePLNBalanceWithDifferentAccounts() {
    assertEquals(
        SAVINGS_ACCOUNT_PLN_VALUE
            .add(SAVINGS_ACCOUNT_USD_VALUE.multiply(BigDecimal.valueOf(USD_PLN_VALUE)))
            .add(BigDecimal.ZERO),
        balanceService.calculateTotalBalance(
            List.of(SAVINGS_ACCOUNT_PLN, SAVINGS_ACCOUNT_USD, INVESTMENT_ACCOUNT_STOCKS),
            Currency.PLN)
    );
  }

}
