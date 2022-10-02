package pp.pl.io.savings.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pp.pl.io.savings.account.AccountRepository;
import pp.pl.io.savings.account.AccountService;
import pp.pl.io.savings.account.UserAccountRepository;
import pp.pl.io.savings.account.UserAccountService;
import pp.pl.io.savings.account.balance.BalanceService;
import pp.pl.io.savings.account.balance.CurrencyService;
import pp.pl.io.savings.account.id.UuidService;
import pp.pl.io.savings.exchange.ExchangeRatesStructure;
import pp.pl.io.savings.organisation.SavingsSecurityService;

@Configuration
public class UsecaseConfiguration {

  @Bean
  CurrencyService currencyService(final ExchangeRatesStructure exchangeRatesStructure) {
    return new CurrencyService(exchangeRatesStructure);
  }

  @Bean
  BalanceService balanceService(final CurrencyService currencyService) {
    return new BalanceService(currencyService);
  }

  @Bean
  UserAccountService userAccountService(final UserAccountRepository userAccountRepository,
                                        final AccountRepository accountRepository,
                                        final BalanceService balanceService,
                                        final SavingsSecurityService savingsSecurityService) {
    return new UserAccountService(userAccountRepository, accountRepository, balanceService, savingsSecurityService);
  }

  @Bean
  UuidService uuidService() {
    return new UuidService();
  }

  @Bean
  AccountService accountService(final AccountRepository accountRepository,
                                final SavingsSecurityService savingsSecurityService,
                                final UuidService uuidService,
                                final BalanceService balanceService) {
    return new AccountService(accountRepository, savingsSecurityService, uuidService, balanceService);
  }
}
