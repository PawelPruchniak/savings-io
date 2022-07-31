package pp.pl.io.savings;

import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import pp.pl.io.savings.account.UserAccount;
import pp.pl.io.savings.account.UserAccountRepository;

import java.math.BigDecimal;

@Slf4j
@AllArgsConstructor
public class DbUserAccountRepository implements UserAccountRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  @Override
  public Try<Option<UserAccount>> fetchUserAccount() {
    return Try.of(() -> {
          log.debug("Fetching user account");

          final Option<UserAccount> userAccount = Option.of(
                  List.ofAll(
                      jdbcTemplate.query(
                          "select main_currency as main_currency from user_account",
                          (rs, i) -> UserAccount.builder()
                              .currency(rs.getString("main_currency"))
                              .totalBalance(BigDecimal.ZERO)
                              .build()
                      )
                  )
              )
              .getOrElse(List.empty())
              .headOption();

          if (userAccount.isDefined()) {
            // todo: get all related accounts
            return userAccount;
          }

          return userAccount;
        }
    );
  }
}
