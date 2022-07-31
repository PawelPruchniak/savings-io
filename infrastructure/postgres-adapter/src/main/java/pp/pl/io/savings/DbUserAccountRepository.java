package pp.pl.io.savings;

import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import pp.pl.io.savings.account.UserAccount;
import pp.pl.io.savings.account.UserAccountRepository;

@Slf4j
@AllArgsConstructor
public class DbUserAccountRepository implements UserAccountRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  @Override
  public Try<Option<UserAccount>> fetchUserAccount() {
    return null;
  }
}
