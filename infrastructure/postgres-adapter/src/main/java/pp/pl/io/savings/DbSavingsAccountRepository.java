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
import pp.pl.io.savings.account.command.SavingsAccountCommand;
import pp.pl.io.savings.account.create.NewAccount;

import static pp.pl.io.savings.DbAccountRepository.*;
import static pp.pl.io.savings.DbUserAccountRepository.USER_ID_CODE;

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
                    val command = (SavingsAccountCommand) newSavingsAccount.accountCommand();
                    log.debug("Creating savings account with command: {}, for user with id: {}", command, newSavingsAccount.userId());
                    Validate.notNull(newSavingsAccount);

                    jdbcTemplate.update(
                            "insert into account (id, user_id, name, description, account_type) " +
                                    "values (:accountId, :userId, :name, :description, :accountType)",
                            new MapSqlParameterSource()
                                    .addValue(ACCOUNT_ID_CODE, newSavingsAccount.newAccountId().code)
                                    .addValue(USER_ID_CODE, newSavingsAccount.userId().code)
                                    .addValue(NAME_ID_CODE, command.getName())
                                    .addValue(DESCRIPTION_ID_CODE, command.getDescription())
                                    .addValue(ACCOUNT_TYPE_ID_CODE, command.getAccountType().name())
                    );

                    jdbcTemplate.update(
                            "insert into savings_account (account_id, currency, balance) " +
                                    "values (:accountId, :currency, :balance)",
                            new MapSqlParameterSource()
                                    .addValue(ACCOUNT_ID_CODE, newSavingsAccount.newAccountId().code)
                                    .addValue(CURRENCY_CODE, command.getCurrency().name())
                                    .addValue(BALANCE_CODE, command.getBalance())
                    );
                }
        );
    }
}
