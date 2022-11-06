package pp.pl.io.savings.dto.response;

import lombok.Builder;
import lombok.Value;
import pp.pl.io.savings.account.AccountType;

@Value
@Builder
public class InvestmentAccountDTO implements AccountDTO {

  String accountId;
  String name;
  String description;
  String asset;
  Double assetQuantity;
  String currencyInvested;
  Double amountInvested;
  Double investmentResultValue;
  Double investmentResultPercentage;
  String accountType = AccountType.INVESTMENT.name();
}
