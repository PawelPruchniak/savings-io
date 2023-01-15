package pp.pl.io.savings.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pp.pl.io.savings.domain.account.AccountRepository;
import pp.pl.io.savings.domain.account.UserAccountRepository;
import pp.pl.io.savings.domain.exchange.ExchangeRatesStructure;
import pp.pl.io.savings.domain.organisation.SecurityService;
import pp.pl.io.savings.usecases.account.usecases.AccountService;
import pp.pl.io.savings.usecases.account.usecases.UserAccountService;
import pp.pl.io.savings.usecases.account.usecases.balance.BalanceService;
import pp.pl.io.savings.usecases.account.usecases.balance.CurrencyService;
import pp.pl.io.savings.usecases.account.usecases.id.UuidService;

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
                                        final SecurityService savingsSecurityService) {
    return new UserAccountService(userAccountRepository, accountRepository, balanceService, savingsSecurityService);
  }

  @Bean
  UuidService uuidService() {
    return new UuidService();
  }

  @Bean
  AccountService accountService(final AccountRepository accountRepository,
                                final SecurityService savingsSecurityService,
                                final UuidService uuidService,
                                final BalanceService balanceService) {
    return new AccountService(accountRepository, savingsSecurityService, uuidService, balanceService);
  }
}
