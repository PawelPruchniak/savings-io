package pp.pl.io.savings.account.create;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import pp.pl.io.savings.account.AccountType;
import pp.pl.io.savings.account.Currency;

@Value
@Builder
@EqualsAndHashCode
public class SavingsAccountCommand implements AccountCommand {

  String name;
  String description;
  @NonNull Currency currency;
  @NonNull Double balance;
  @NonNull AccountType accountType = AccountType.SAVINGS;

}
