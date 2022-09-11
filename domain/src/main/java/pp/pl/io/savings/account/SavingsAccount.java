package pp.pl.io.savings.account;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class SavingsAccount implements Account {

  @NonNull AccountId accountId;

  String name;

  String description;

  @NonNull Currency currency;

  @Builder.Default
  @NonNull
  BigDecimal balance = BigDecimal.ZERO;


  @Override
  public BigDecimal getValue() {
    return balance;
  }

  @Override
  public AccountType getAccountType() {
    return AccountType.SAVINGS;
  }
}
