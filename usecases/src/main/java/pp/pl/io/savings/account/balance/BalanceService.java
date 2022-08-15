package pp.pl.io.savings.account.balance;

import io.vavr.collection.List;
import lombok.AllArgsConstructor;
import pp.pl.io.savings.account.Account;
import pp.pl.io.savings.account.AccountType;
import pp.pl.io.savings.account.Currency;
import pp.pl.io.savings.account.SavingsAccount;

import java.math.BigDecimal;

@AllArgsConstructor
public class BalanceService {

  private final CurrencyService currencyService;

  public BigDecimal calculateTotalBalance(final List<Account> accounts, final Currency mainCurrency) {
    return accounts
        .map(account -> extractBalance(account, mainCurrency))
        .reduceOption(BigDecimal::add)
        .getOrElse(BigDecimal.ZERO);
  }

  private BigDecimal extractBalance(final Account account, final Currency mainCurrency) {
    if (account.getAccountType() == AccountType.SAVINGS) {
      return extractSavingsBalance((SavingsAccount) account, mainCurrency);
    }
    throw new IllegalArgumentException("This account type: " + account.getAccountType() + " is not supported");
  }

  private BigDecimal extractSavingsBalance(final SavingsAccount savingsAccount, final Currency mainCurrency) {
    return currencyService.recalculateValue(savingsAccount.getBalance(), savingsAccount.getCurrency(), mainCurrency);
  }
}
