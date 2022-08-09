package pp.pl.io.savings.dto.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SavingsAccountDTO implements AccountDTO {

  String accountId;
  String name;
  String description;
  String currency;
  Double balance;
  String accountType;
}
