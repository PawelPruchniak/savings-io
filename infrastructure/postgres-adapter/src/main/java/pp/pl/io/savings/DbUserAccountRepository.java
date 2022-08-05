package pp.pl.io.savings;

import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import pp.pl.io.savings.account.Currency;
import pp.pl.io.savings.account.UserAccount;
import pp.pl.io.savings.account.UserAccountRepository;

import java.math.BigDecimal;

@Slf4j
@AllArgsConstructor
public class DbUserAccountRepository implements UserAccountRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  private static final String USERNAME_CODE = "username";


  @Override
  public Try<Option<UserAccount>> fetchUserAccount(final String username) {
    return Try.of(() -> {
          Validate.notBlank(username);
          log.debug("Fetching user account for username: {}", username);

          final Option<UserAccount> userAccount = Option.of(
                  List.ofAll(
                      jdbcTemplate.query(
                          "select main_currency as main_currency from user_account ua" + "\n" +
                              "where ua.user_id = (select id from user_profile up where up.username =:" + USERNAME_CODE + ")",
                          new MapSqlParameterSource()
                              .addValue(USERNAME_CODE, username),
                          (rs, i) -> UserAccount.builder()
                              .currency(Currency.valueOf(rs.getString("main_currency")))
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
