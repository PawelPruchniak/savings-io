package pp.pl.io.savings.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import pp.pl.io.savings.DbAccountRepository;
import pp.pl.io.savings.DbUserAccountRepository;
import pp.pl.io.savings.account.AccountRepository;
import pp.pl.io.savings.account.UserAccountRepository;

@Configuration
public class InfrastructureConfiguration {

  @Bean
  UserAccountRepository userAccountRepository(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    return new DbUserAccountRepository(namedParameterJdbcTemplate);
  }

  @Bean
  AccountRepository accountRepository(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    return new DbAccountRepository(namedParameterJdbcTemplate);
  }
}
