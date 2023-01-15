package pp.pl.io.savings.web.dto.response;

import lombok.Builder;
import lombok.Value;
import pp.pl.io.savings.domain.account.AccountType;

@Value
@Builder
public class SavingsAccountDTO implements AccountDTO {

  String accountId;
  String name;
  String description;
  String currency;
  Double balance;
  String accountType = AccountType.SAVINGS.name();
}
