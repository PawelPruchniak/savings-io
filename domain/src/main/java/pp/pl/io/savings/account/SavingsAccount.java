package pp.pl.io.savings.account;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import pp.pl.io.savings.account.asset.Currency;

import java.math.BigDecimal;

@Value
@Builder
public class SavingsAccount implements Account {

  @NonNull AccountId accountId;

  @NonNull String name;

  String description;

  @NonNull Currency currency;

  @Builder.Default
  @NonNull
  BigDecimal balance = BigDecimal.ZERO;

  @Override
  public AccountType getAccountType() {
    return AccountType.SAVINGS;
  }
}
