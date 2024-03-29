package pp.pl.io.savings.web.dto.request.create;

import lombok.Builder;
import lombok.Value;
import pp.pl.io.savings.domain.account.AccountType;

@Value
@Builder
public class SavingsAccountRequest implements AccountRequest {

  String name;
  String description;
  String currency;
  Double balance;
  String accountType = AccountType.SAVINGS.name();
}
