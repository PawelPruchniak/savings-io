package pp.pl.io.savings.account;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class UserAccount {

  @NonNull Currency currency;
  @NonNull BigDecimal totalBalance;
}
