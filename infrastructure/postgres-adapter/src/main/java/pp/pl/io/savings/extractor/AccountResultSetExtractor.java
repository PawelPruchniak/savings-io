package pp.pl.io.savings.extractor;

import io.vavr.collection.List;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import pp.pl.io.savings.account.*;

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
  private Account extractAccount(final ResultSet rs) {
    var accountType = rs.getString("a_account_type");

    return switch (accountType) {
      case "SAVINGS" -> extractSavingsAccount(rs);
      case "INVESTMENT" -> extractInvestmentAccount(rs);
      default -> throw new IllegalArgumentException("Unknown account type: " + accountType);
    };
  }

  @SneakyThrows
  private SavingsAccount extractSavingsAccount(final ResultSet rs) {
    return SavingsAccount.builder()
        .accountId(AccountId.of(rs.getString("a_account_id")))
        .name(rs.getString("a_name"))
        .description(rs.getString("a_description"))
        .currency(Currency.valueOf(rs.getString("sa_currency")))
        .balance(rs.getBigDecimal("sa_balance"))
        .build();
  }

  @SneakyThrows
  private InvestmentAccount extractInvestmentAccount(final ResultSet rs) {
    return InvestmentAccount.builder()
        .accountId(AccountId.of(rs.getString("a_account_id")))
        .name(rs.getString("a_name"))
        .description(rs.getString("a_description"))
        .asset(rs.getString("ia_asset"))
        .assetQuantity(rs.getBigDecimal("ia_asset_quantity"))
        .currencyInvested(Currency.valueOf(rs.getString("ia_currency_invested")))
        .amountInvested(rs.getBigDecimal("ia_amount_invested"))
        .build();
  }
}
