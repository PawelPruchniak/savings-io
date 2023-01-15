package pp.pl.io.savings.db;

import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.Validate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import pp.pl.io.savings.domain.account.Account;
import pp.pl.io.savings.domain.account.create.NewAccount;
import pp.pl.io.savings.domain.account.create.SavingsAccountCommand;
import pp.pl.io.savings.domain.account.update.SavingsAccountUpdateCommand;

import static pp.pl.io.savings.db.DbAccountRepository.*;
import static pp.pl.io.savings.db.DbUserAccountRepository.USER_ID_CODE;

@Slf4j
@AllArgsConstructor
public class DbSavingsAccountRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

  private static final String CURRENCY_CODE = "currency";
  private static final String BALANCE_CODE = "balance";

  @Transactional
  public Try<Void> deleteSavingsAccount(final Account account) {
    return Try.run(() -> {
          log.debug("Deleting savings account with id: {}", account.getAccountId());
          Validate.notNull(account);

          val sqlParams = new MapSqlParameterSource()
              .addValue(ACCOUNT_ID_CODE, account.getAccountId().code);

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

  @Transactional
  public Try<Void> createSavingsAccount(final NewAccount newSavingsAccount) {
    return Try.run(() -> {
          val createCommand = (SavingsAccountCommand) newSavingsAccount.accountCommand();
          log.debug("Creating savings account with command: {}, for user with id: {}", createCommand, newSavingsAccount.userId());
          Validate.notNull(newSavingsAccount);

          jdbcTemplate.update(
              "insert into account (id, user_id, name, description, account_type) " +
                  "values (:accountId, :userId, :name, :description, :accountType)",
              new MapSqlParameterSource()
                  .addValue(ACCOUNT_ID_CODE, newSavingsAccount.newAccountId().code)
                  .addValue(USER_ID_CODE, newSavingsAccount.userId().code)
                  .addValue(NAME_CODE, createCommand.getName())
                  .addValue(DESCRIPTION_CODE, createCommand.getDescription())
                  .addValue(ACCOUNT_TYPE_CODE, createCommand.getAccountType().name())
          );

          jdbcTemplate.update(
              "insert into savings_account (account_id, currency, balance) " +
                  "values (:accountId, :currency, :balance)",
              new MapSqlParameterSource()
                  .addValue(ACCOUNT_ID_CODE, newSavingsAccount.newAccountId().code)
                  .addValue(CURRENCY_CODE, createCommand.getCurrency().getCode())
                  .addValue(BALANCE_CODE, createCommand.getBalance())
          );
        }
    );
  }

  @Transactional
  public Try<Void> updateSavingsAccount(final SavingsAccountUpdateCommand updateCommand) {
    return Try.run(() -> {
          log.debug("Updating savings account with command: {}", updateCommand);
          Validate.notNull(updateCommand);

          jdbcTemplate.update(
              "update account " +
                  "set name =:" + NAME_CODE + "," +
                  "    description =:" + DESCRIPTION_CODE + " " +
                  "where id =:" + ACCOUNT_ID_CODE,
              new MapSqlParameterSource()
                  .addValue(NAME_CODE, updateCommand.getName())
                  .addValue(DESCRIPTION_CODE, updateCommand.getDescription())
                  .addValue(ACCOUNT_ID_CODE, updateCommand.getAccountId().code)
          );

          jdbcTemplate.update(
              "update savings_account " +
                  "set currency =:" + CURRENCY_CODE + ", " +
                  "    balance =:" + BALANCE_CODE + " " +
                  "where account_id =:" + ACCOUNT_ID_CODE,
              new MapSqlParameterSource()
                  .addValue(CURRENCY_CODE, updateCommand.getCurrency().getCode())
                  .addValue(BALANCE_CODE, updateCommand.getBalance())
                  .addValue(ACCOUNT_ID_CODE, updateCommand.getAccountId().code)
          );
        }
    );
  }
}
