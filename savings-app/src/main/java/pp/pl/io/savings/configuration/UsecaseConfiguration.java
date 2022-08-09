package pp.pl.io.savings.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pp.pl.io.savings.AccountService;
import pp.pl.io.savings.UserAccountService;
import pp.pl.io.savings.account.AccountRepository;
import pp.pl.io.savings.account.UserAccountRepository;
import pp.pl.io.savings.organisation.SavingsSecurityService;

@Configuration
public class UsecaseConfiguration {

  @Bean
  UserAccountService userAccountService(UserAccountRepository userAccountRepository,
                                        AccountRepository accountRepository,
                                        SavingsSecurityService savingsSecurityService) {
    return new UserAccountService(userAccountRepository, accountRepository, savingsSecurityService);
  }

  @Bean
  AccountService accountService(AccountRepository accountRepository,
                                SavingsSecurityService savingsSecurityService) {
    return new AccountService(accountRepository, savingsSecurityService);
  }
}
