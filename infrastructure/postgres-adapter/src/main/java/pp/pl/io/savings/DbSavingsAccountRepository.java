package pp.pl.io.savings;

import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.Validate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import pp.pl.io.savings.account.Account;

import static pp.pl.io.savings.DbAccountRepository.ACCOUNT_ID_CODE;

@Slf4j
@AllArgsConstructor
public class DbSavingsAccountRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  @Transactional
  public Try<Void> deleteSavingsAccount(final Account account) {
    return Try.run(() -> {
          log.debug("Deleting savings account with id: {}", account.getAccountId());
          Validate.notNull(account);

          val sqlParams = new MapSqlParameterSource()
              .addValue(ACCOUNT_ID_CODE, Integer.valueOf(account.getAccountId().code));

          jdbcTemplate.update(
              "delete from savings_account sa " +
                  "where sa.account_id =:" + ACCOUNT_ID_CODE,
              sqlParams
          );

          jdbcTemplate.update(
              "delete from account a " +
                  "where a.id =:" + ACCOUNT_ID_CODE,
              sqlParams
          );
        }
    );
  }
}
