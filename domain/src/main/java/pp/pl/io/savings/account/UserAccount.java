package pp.pl.io.savings.account;

import io.vavr.collection.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import pp.pl.io.savings.account.asset.Currency;

import java.math.BigDecimal;

@Value
@Builder(toBuilder = true)
public class UserAccount {

  @Builder.Default
  @NonNull List<Account> accounts = List.empty();

  @NonNull Currency currency;
  @NonNull BigDecimal totalBalance;
}
