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
import pp.pl.io.savings.account.asset.Currency;
import pp.pl.io.savings.organisation.UserId;

import java.math.BigDecimal;

@Slf4j
@AllArgsConstructor
public class DbUserAccountRepository implements UserAccountRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  protected static final String USER_ID_CODE = "userId";

  @Override
  public Try<Option<UserAccount>> fetchUserAccount(final UserId userId) {
    return Try.of(() -> {
          log.debug("Fetching user account for user with id: {}", userId);
          Validate.notNull(userId);

          return Option.of(
                  List.ofAll(
                      jdbcTemplate.query(
                          "select main_currency as main_currency from user_account ua" + "\n" +
                              "where ua.user_id =:" + USER_ID_CODE,
                          new MapSqlParameterSource()
                              .addValue(USER_ID_CODE, userId.code),
                          (rs, i) -> UserAccount.builder()
                              .currency(Currency.of(rs.getString("main_currency")))
                              .totalBalance(BigDecimal.ZERO)
                              .build()
                      )
                  )
              )
              .getOrElse(List.empty())
              .headOption();
        }
    );
  }
}
