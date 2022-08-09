package pp.pl.io.savings;

import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import pp.pl.io.savings.account.Account;
import pp.pl.io.savings.account.AccountRepository;
import pp.pl.io.savings.extractor.AccountResultSetExtractor;

import java.util.Objects;

@Slf4j
@AllArgsConstructor
public class DbAccountRepository implements AccountRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final AccountResultSetExtractor accountResultSetExtractor = new AccountResultSetExtractor();

  private static final String SELECT_SAVINGS_ACCOUNT =
      "select a.id as a_account_id, a.name as a_name, a.description as a_description, " +
          "a.account_type as a_account_type, " +
          "sa.currency as sa_currency, sa.balance as sa_balance " +
          "from account a " +
          "left outer join savings_account sa on a.id = sa.account_id ";

  private static final String ACCOUNT_ID_CODE = "accountId";
  private static final String USERNAME_CODE = "username";

  @Override
  public Try<Option<Account>> fetchAccount(String accountId, String username) {
    return Try.of(() -> {
          Validate.notBlank(accountId);
          Validate.notBlank(username);
          log.debug("Fetching account with id: {} for username: {}", accountId, username);

          return Option.of(
                  List.ofAll(
                      Objects.requireNonNull(
                          jdbcTemplate.query(
                              SELECT_SAVINGS_ACCOUNT + "\n" +
                                  "where a.id =:" + ACCOUNT_ID_CODE + "\n" +
                                  "and a.user_id = (select up.id from user_profile up where up.username =:" + USERNAME_CODE + ")",
                              new MapSqlParameterSource()
                                  .addValue(ACCOUNT_ID_CODE, Integer.valueOf(accountId))
                                  .addValue(USERNAME_CODE, username),
                              accountResultSetExtractor
                          )
                      )
                  )
              )
              .getOrElse(List.empty())
              .headOption();
        }
    );
  }
}
