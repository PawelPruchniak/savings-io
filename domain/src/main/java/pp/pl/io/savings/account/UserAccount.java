package pp.pl.io.savings.account;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class UserAccount {

  @NonNull String currency;
  @NonNull BigDecimal totalBalance;
}