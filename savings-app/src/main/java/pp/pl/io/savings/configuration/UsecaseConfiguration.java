package pp.pl.io.savings.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pp.pl.io.savings.UserAccountService;
import pp.pl.io.savings.account.UserAccountRepository;

@Configuration
public class UsecaseConfiguration {

  @Bean
  UserAccountService userAccountService(UserAccountRepository userAccountRepository) {
    return new UserAccountService(userAccountRepository);
  }
}
