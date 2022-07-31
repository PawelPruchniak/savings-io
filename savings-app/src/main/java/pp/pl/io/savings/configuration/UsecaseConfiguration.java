package pp.pl.io.savings.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pp.pl.io.savings.UserAccountService;

@Configuration
public class UsecaseConfiguration {

  @Bean
  UserAccountService userAccountService() {
    return new UserAccountService();
  }
}
