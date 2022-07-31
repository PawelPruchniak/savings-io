package pp.pl.io.savings.dto.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserAccountDTO {

  String currency;
  Double totalBalance;
}
