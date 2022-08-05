package pp.pl.io.savings;

import pp.pl.io.savings.account.Currency;
import pp.pl.io.savings.account.UserAccount;

import java.math.BigDecimal;

public class ServiceTestData {

  // User
  protected static final String SOME_USERNAME = "some-username@gmail.com";

  // User Account
  protected static final BigDecimal ONE_DECIMAL_PLACE_VALUE = BigDecimal.valueOf(501.1);
  protected static final UserAccount USER_ACCOUNT_PLN = UserAccount.builder()
      .currency(Currency.valueOf("PLN"))
      .totalBalance(ONE_DECIMAL_PLACE_VALUE)
      .build();

  // Errors
  protected static final RuntimeException SOME_PROCESSING_ERROR = new RuntimeException("Some processing error");
}
