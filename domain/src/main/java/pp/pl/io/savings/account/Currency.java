package pp.pl.io.savings.account;

public enum Currency {

  PLN("Polish złoty"),
  EUR("Euro"),
  USD("United States dollar");

  public final String description;

  Currency(String description) {
    this.description = description;
  }
}
