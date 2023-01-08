package pp.pl.io.savings.domain.account.update;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import pp.pl.io.savings.domain.account.AccountId;
import pp.pl.io.savings.domain.account.AccountType;
import pp.pl.io.savings.domain.account.asset.Currency;

@Value
@Builder
@EqualsAndHashCode
public class SavingsAccountUpdateCommand implements AccountUpdateCommand {

  @NonNull AccountId accountId;
  @NonNull String name;
  String description;
  @NonNull Currency currency;
  @NonNull Double balance;
  @NonNull AccountType accountType = AccountType.SAVINGS;

}
