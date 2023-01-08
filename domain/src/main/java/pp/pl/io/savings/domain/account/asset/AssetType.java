package pp.pl.io.savings.domain.account.asset;

public enum AssetType {

  CURRENCY("Currency"),
  STOCKS("Stocks");

  public final String description;

  AssetType(final String description) {
    this.description = description;
  }
}
