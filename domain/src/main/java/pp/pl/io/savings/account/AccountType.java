package pp.pl.io.savings.account;

public enum AccountType {

  SAVINGS("Account that holds money in chosen currency");

  public final String description;

  AccountType(String description) {
    this.description = description;
  }
}
