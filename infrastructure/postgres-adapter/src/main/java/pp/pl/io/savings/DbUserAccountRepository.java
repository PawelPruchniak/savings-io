package pp.pl.io.savings;

import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import pp.pl.io.savings.account.UserAccount;
import pp.pl.io.savings.account.UserAccountRepository;

import java.math.BigDecimal;

@Slf4j
@AllArgsConstructor
public class DbUserAccountRepository implements UserAccountRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  private static final String EMAIL_CODE = "email";


  @Override
  public Try<Option<UserAccount>> fetchUserAccount(final String userEmail) {
    return Try.of(() -> {
          Validate.notBlank(userEmail);
          log.debug("Fetching user account for username: {}", userEmail);

          final Option<UserAccount> userAccount = Option.of(
                  List.ofAll(
                      jdbcTemplate.query(
                          "select ua.main_currency as main_currency from user_account ua" + "\n" +
                              "where ua.user_id = (select u.id from user u where u.email =:" + EMAIL_CODE + ")",
                          new MapSqlParameterSource()
                              .addValue(EMAIL_CODE, userEmail),
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
