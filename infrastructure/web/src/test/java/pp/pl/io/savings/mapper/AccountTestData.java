package pp.pl.io.savings.mapper;

import pp.pl.io.savings.account.UserAccount;

import java.math.BigDecimal;

public class AccountTestData {

  protected static final BigDecimal ONE_DECIMAL_PLACE_VALUE = BigDecimal.valueOf(501.1);
  protected static final BigDecimal TWO_DECIMAL_PLACE_VALUE = BigDecimal.valueOf(2870.82);
  protected static final BigDecimal FOUR_DECIMAL_PLACE_VALUE = BigDecimal.valueOf(3.9876);

  protected static final UserAccount USER_ACCOUNT_PLN = UserAccount.builder()
      .currency("PLN")
      .totalBalance(ONE_DECIMAL_PLACE_VALUE)
      .build();

  protected static final UserAccount USER_ACCOUNT_EUR = UserAccount.builder()
      .currency("EUR")
      .totalBalance(TWO_DECIMAL_PLACE_VALUE)
      .build();

  protected static final UserAccount USER_ACCOUNT_USD = UserAccount.builder()
      .currency("USD")
      .totalBalance(FOUR_DECIMAL_PLACE_VALUE)
      .build();
}
