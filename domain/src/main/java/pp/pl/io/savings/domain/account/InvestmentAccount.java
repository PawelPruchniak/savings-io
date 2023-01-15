package pp.pl.io.savings.domain.account;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import pp.pl.io.savings.domain.account.asset.Asset;
import pp.pl.io.savings.domain.account.asset.Currency;

import java.math.BigDecimal;

@Value
@Builder(toBuilder = true)
public class InvestmentAccount implements Account {

  @NonNull AccountId accountId;

  @NonNull String name;

  String description;

  @NonNull Asset asset;

  @NonNull BigDecimal assetQuantity;

  @NonNull Currency currencyInvested;

  @NonNull BigDecimal amountInvested;

  @Builder.Default
  @NonNull BigDecimal investmentResultValue = BigDecimal.ZERO;

  @Builder.Default
  @NonNull BigDecimal investmentResultPercentage = BigDecimal.ZERO;

  @Override
  public AccountType getAccountType() {
    return AccountType.INVESTMENT;
  }

}
