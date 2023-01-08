package pp.pl.io.savings.web.dto.request.update;

import lombok.Builder;
import lombok.Value;
import pp.pl.io.savings.domain.account.AccountType;

@Value
@Builder
public class SavingsAccountUpdateRequest implements AccountUpdateRequest {

  String accountId;
  String name;
  String description;
  String currency;
  Double balance;
  String accountType = AccountType.SAVINGS.name();
}
