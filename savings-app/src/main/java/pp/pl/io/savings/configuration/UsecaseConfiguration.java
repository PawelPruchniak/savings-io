package pp.pl.io.savings.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pp.pl.io.savings.account.AccountRepository;
import pp.pl.io.savings.account.AccountService;
import pp.pl.io.savings.account.UserAccountRepository;
import pp.pl.io.savings.account.UserAccountService;
import pp.pl.io.savings.account.balance.BalanceService;
import pp.pl.io.savings.account.balance.CurrencyService;
import pp.pl.io.savings.organisation.SavingsSecurityService;

@Configuration
public class UsecaseConfiguration {

  @Bean
  CurrencyService currencyService() {
    return new CurrencyService();
  }

  @Bean
  BalanceService balanceService(CurrencyService currencyService) {
    return new BalanceService(currencyService);
  }

  @Bean
  UserAccountService userAccountService(UserAccountRepository userAccountRepository,
                                        AccountRepository accountRepository,
                                        BalanceService balanceService,
                                        SavingsSecurityService savingsSecurityService) {
    return new UserAccountService(userAccountRepository, accountRepository, balanceService, savingsSecurityService);
  }

  @Bean
  AccountService accountService(AccountRepository accountRepository,
                                SavingsSecurityService savingsSecurityService) {
    return new AccountService(accountRepository, savingsSecurityService);
  }
}
