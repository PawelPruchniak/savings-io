package pp.pl.io.savings.account.balance;

import io.vavr.collection.List;
import org.junit.jupiter.api.Test;
import pp.pl.io.savings.account.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pp.pl.io.savings.ServiceTestData.*;

class BalanceServiceTest {

  private final BalanceService balanceService = new BalanceService();

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
        SAVINGS_ACCOUNT_PLN_VALUE,
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
        SAVINGS_ACCOUNT_USD_VALUE,
        balanceService.calculateTotalBalance(List.of(SAVINGS_ACCOUNT_PLN), Currency.USD)
    );
  }

  @Test
  void shouldCalculatePLNBalanceWithFewSavingsAccounts() {
    assertEquals(
        SAVINGS_ACCOUNT_PLN_VALUE,
        balanceService.calculateTotalBalance(List.of(SAVINGS_ACCOUNT_PLN, SAVINGS_ACCOUNT_USD), Currency.PLN)
    );
  }

}
