package pp.pl.io.savings.account.balance;

import io.vavr.collection.List;
import lombok.AllArgsConstructor;
import pp.pl.io.savings.account.Account;
import pp.pl.io.savings.account.InvestmentAccount;
import pp.pl.io.savings.account.SavingsAccount;
import pp.pl.io.savings.account.asset.Currency;

import java.math.BigDecimal;

@AllArgsConstructor
public class BalanceService {

  private final CurrencyService currencyService;

  public BigDecimal calculateTotalBalance(final List<Account> accounts, final Currency mainCurrency) {
    return accounts
        .map(account -> calculateBalance(account, mainCurrency))
        .reduceOption(BigDecimal::add)
        .getOrElse(BigDecimal.ZERO);
  }

  public BigDecimal calculateBalance(final Account account, final Currency mainCurrency) {
    return switch (account.getAccountType()) {
      case SAVINGS -> calculateSavingsBalance((SavingsAccount) account, mainCurrency);
      case INVESTMENT -> calculateInvestmentBalance((InvestmentAccount) account, mainCurrency);
    };
  }

  private BigDecimal calculateSavingsBalance(final SavingsAccount savingsAccount, final Currency mainCurrency) {
    return currencyService.recalculateValue(savingsAccount.getBalance(), savingsAccount.getCurrency(), mainCurrency);
  }

  private BigDecimal calculateInvestmentBalance(final InvestmentAccount investmentAccount, final Currency mainCurrency) {
    return currencyService.recalculateValue(investmentAccount.getAssetQuantity(), investmentAccount.getAsset(), mainCurrency);
  }
}
