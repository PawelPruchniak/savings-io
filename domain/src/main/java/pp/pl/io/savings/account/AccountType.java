package pp.pl.io.savings.account;

public enum AccountType {

  SAVINGS("Account that holds money in chosen currency"),
  INVESTMENT("Account that holds assets of investment. Stocks, crypto");

  public final String description;

  AccountType(String description) {
    this.description = description;
  }
}
