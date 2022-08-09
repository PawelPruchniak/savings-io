package pp.pl.io.savings.extractor;

import io.vavr.collection.List;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import pp.pl.io.savings.account.Account;
import pp.pl.io.savings.account.AccountType;
import pp.pl.io.savings.account.Currency;
import pp.pl.io.savings.account.SavingsAccount;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccountResultSetExtractor implements ResultSetExtractor<List<Account>> {

  @Override
  public List<Account> extractData(@NonNull ResultSet rs) throws SQLException, DataAccessException {
    java.util.List<Account> accounts = new ArrayList<>();

    while (rs.next()) {
      accounts.add(extractAccount(rs));
    }

    return List.ofAll(accounts);
  }

  @SneakyThrows
  private Account extractAccount(ResultSet rs) {
    var accountType = rs.getString("a_account_type");

    if (accountType.equals(AccountType.SAVINGS.name())) {
      return extractSavingsAccount(rs);
    }

    throw new IllegalArgumentException("Unknown account type: " + accountType);
  }

  @SneakyThrows
  private SavingsAccount extractSavingsAccount(ResultSet rs) {
    return SavingsAccount.builder()
        .accountId(rs.getString("a_account_id"))
        .name(rs.getString("a_name"))
        .description(rs.getString("a_description"))
        .currency(Currency.valueOf(rs.getString("sa_currency")))
        .balance(rs.getBigDecimal("sa_balance"))
        .build();
  }
}
