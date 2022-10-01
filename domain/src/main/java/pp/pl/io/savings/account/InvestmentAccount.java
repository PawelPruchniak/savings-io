package pp.pl.io.savings.account;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class InvestmentAccount implements Account {

  @NonNull AccountId accountId;

  @NonNull String name;

  String description;

  @NonNull String asset;

  @Builder.Default
  @NonNull BigDecimal assetQuantity = BigDecimal.ZERO;

  @NonNull Currency currencyInvested;

  @Builder.Default
  @NonNull BigDecimal amountInvested = BigDecimal.ZERO;

  @Builder.Default
  @NonNull BigDecimal investmentResultValue = BigDecimal.ZERO;

  @Builder.Default
  @NonNull BigDecimal investmentResultPercentage = BigDecimal.ZERO;

  @Override
  public AccountType getAccountType() {
    return AccountType.INVESTMENT;
  }

}
