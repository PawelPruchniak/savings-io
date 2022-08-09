package pp.pl.io.savings.account;


import java.math.BigDecimal;

public interface Account {

  String getAccountId();

  String getName();

  String getDescription();

  BigDecimal getValue();

  AccountType getAccountType();
}
