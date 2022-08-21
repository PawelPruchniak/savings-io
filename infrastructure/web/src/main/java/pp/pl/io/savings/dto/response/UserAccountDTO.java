package pp.pl.io.savings.dto.response;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class UserAccountDTO {

  String currency;
  Double totalBalance;

  List<String> subAccountsIds;
}
