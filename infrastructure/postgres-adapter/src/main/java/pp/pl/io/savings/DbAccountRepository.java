package pp.pl.io.savings;

import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import pp.pl.io.savings.account.Account;
import pp.pl.io.savings.account.AccountId;
import pp.pl.io.savings.account.AccountRepository;
import pp.pl.io.savings.account.AccountType;
import pp.pl.io.savings.account.create.NewAccount;
import pp.pl.io.savings.extractor.AccountResultSetExtractor;
import pp.pl.io.savings.organisation.UserId;

import java.util.Objects;

import static pp.pl.io.savings.DbUserAccountRepository.USER_ID_CODE;

@Slf4j
public class DbAccountRepository implements AccountRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final AccountResultSetExtractor accountResultSetExtractor = new AccountResultSetExtractor();
  private final DbSavingsAccountRepository savingsAccountRepository;

  public DbAccountRepository(final NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    this.savingsAccountRepository = new DbSavingsAccountRepository(jdbcTemplate);
  }

  private static final String SELECT_SAVINGS_ACCOUNT =
      "select a.id as a_account_id, a.name as a_name, a.description as a_description, " +
          "a.account_type as a_account_type, " +
          "sa.currency as sa_currency, sa.balance as sa_balance " +
          "from account a " +
          "left outer join savings_account sa on a.id = sa.account_id ";

  protected static final String ACCOUNT_ID_CODE = "accountId";
  protected static final String NAME_ID_CODE = "name";
  protected static final String DESCRIPTION_ID_CODE = "description";
  protected static final String ACCOUNT_TYPE_ID_CODE = "accountType";

  @Override
  public Try<Option<Account>> fetchAccount(final AccountId accountId, final UserId userId) {
    return Try.of(() -> {
      log.debug("Fetching account with id: {} for user with id: {}", accountId, userId);
      Validate.notNull(accountId);
      Validate.notNull(userId);

      return Option.of(
              List.ofAll(
                      Objects.requireNonNull(
                          jdbcTemplate.query(
                              SELECT_SAVINGS_ACCOUNT + "\n" +
                                  "where a.id =:" + ACCOUNT_ID_CODE + "\n" +
                                  "and a.user_id =:" + USER_ID_CODE,
                              new MapSqlParameterSource()
                                  .addValue(ACCOUNT_ID_CODE, accountId.code)
                                  .addValue(USER_ID_CODE, userId.code),
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

  @Override
  public Try<List<Account>> fetchAccounts(final UserId userId) {
    return Try.of(() -> {
          log.debug("Fetching all accounts for user with id: {}", userId);
          Validate.notNull(userId);

          return Option.of(
              List.ofAll(
                  Objects.requireNonNull(
                      jdbcTemplate.query(
                          SELECT_SAVINGS_ACCOUNT + "\n" +
                              "where a.user_id =:" + USER_ID_CODE,
                          new MapSqlParameterSource()
                              .addValue(USER_ID_CODE, userId.code),
                          accountResultSetExtractor
                      )
                  )
              )
          ).getOrElse(List.empty());
        }
    );
  }

  @Override
  public Try<Void> deleteAccount(final Account account) {
    if (account.getAccountType().equals(AccountType.SAVINGS)) {
      return savingsAccountRepository.deleteSavingsAccount(account);
    }
    throw new IllegalArgumentException("This account type: " + account.getAccountType() + " is not supported");
  }

  @Override
  public Try<Void> createAccount(final NewAccount newAccount) {
    if (newAccount.accountCommand().getAccountType().equals(AccountType.SAVINGS)) {
      return savingsAccountRepository.createSavingsAccount(newAccount);
    }
    throw new IllegalArgumentException("This account type: " + newAccount.accountCommand().getAccountType() + " is not supported");
  }
}
