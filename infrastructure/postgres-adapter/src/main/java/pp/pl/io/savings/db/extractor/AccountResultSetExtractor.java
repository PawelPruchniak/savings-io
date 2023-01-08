package pp.pl.io.savings.db.extractor;

import io.vavr.collection.List;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import pp.pl.io.savings.domain.account.Account;
import pp.pl.io.savings.domain.account.AccountId;
import pp.pl.io.savings.domain.account.InvestmentAccount;
import pp.pl.io.savings.domain.account.SavingsAccount;
import pp.pl.io.savings.domain.account.asset.Currency;
import pp.pl.io.savings.domain.account.asset.Stocks;

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
        .currency(Currency.of(rs.getString("sa_currency")))
        .balance(rs.getBigDecimal("sa_balance"))
        .build();
  }

  @SneakyThrows
  private InvestmentAccount extractInvestmentAccount(final ResultSet rs) {
    return InvestmentAccount.builder()
        .accountId(AccountId.of(rs.getString("a_account_id")))
        .name(rs.getString("a_name"))
        .description(rs.getString("a_description"))
        .asset(Stocks.of(rs.getString("ia_asset")))
        .assetQuantity(rs.getBigDecimal("ia_asset_quantity"))
        .currencyInvested(Currency.of(rs.getString("ia_currency_invested")))
        .amountInvested(rs.getBigDecimal("ia_amount_invested"))
        .build();
  }
}
