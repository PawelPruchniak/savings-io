package pp.pl.io.savings.account.balance;

import io.vavr.collection.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pp.pl.io.savings.account.Account;
import pp.pl.io.savings.account.AccountType;
import pp.pl.io.savings.account.Currency;
import pp.pl.io.savings.account.SavingsAccount;

import java.math.BigDecimal;

@Slf4j
@AllArgsConstructor
public class BalanceService {

  //todo: create currency service, private final CurrencyService currencyService;

  public BigDecimal calculateTotalBalance(final List<Account> accounts, final Currency mainCurrency) {
    return accounts
        .map(account -> extractBalance(account, mainCurrency))
        .reduce(BigDecimal::add);
  }

  private BigDecimal extractBalance(final Account account, final Currency mainCurrency) {
    if (account.getAccountType() == AccountType.SAVINGS) {
      return extractSavingsBalance((SavingsAccount) account, mainCurrency);
    }
    throw new IllegalArgumentException("This account type: " + account.getAccountType() + " is not supported");
  }

  private BigDecimal extractSavingsBalance(final SavingsAccount savingsAccount, final Currency mainCurrency) {
    return savingsAccount.getBalance();
  }
}
