package pp.pl.io.savings;

import pp.pl.io.savings.account.Account;
import pp.pl.io.savings.account.Currency;
import pp.pl.io.savings.account.SavingsAccount;
import pp.pl.io.savings.account.UserAccount;
import pp.pl.io.savings.dto.response.AccountDTO;
import pp.pl.io.savings.dto.response.SavingsAccountDTO;
import pp.pl.io.savings.dto.response.UserAccountDTO;
import pp.pl.io.savings.exception.Error;

import java.math.BigDecimal;

public class AccountTestData {

  // User Account
  public static final BigDecimal ONE_DECIMAL_PLACE_VALUE = BigDecimal.valueOf(501.1);
  public static final BigDecimal TWO_DECIMAL_PLACE_VALUE = BigDecimal.valueOf(2870.82);
  public static final BigDecimal FOUR_DECIMAL_PLACE_VALUE = BigDecimal.valueOf(3.9876);

  public static final UserAccount USER_ACCOUNT_PLN = UserAccount.builder()
      .currency(Currency.valueOf("PLN"))
      .totalBalance(ONE_DECIMAL_PLACE_VALUE)
      .build();
  public static final UserAccountDTO USER_ACCOUNT_PLN_DTO = UserAccountDTO.builder()
      .currency("PLN")
      .totalBalance(501.10)
      .build();
  public static final UserAccount USER_ACCOUNT_EUR = UserAccount.builder()
      .currency(Currency.valueOf("EUR"))
      .totalBalance(TWO_DECIMAL_PLACE_VALUE)
      .build();
  public static final UserAccount USER_ACCOUNT_USD = UserAccount.builder()
      .currency(Currency.valueOf("USD"))
      .totalBalance(FOUR_DECIMAL_PLACE_VALUE)
      .build();

  // Account
  public static final String ACCOUNT_ID = "1";
  public static final Account SAVINGS_ACCOUNT = SavingsAccount.builder()
      .accountId(ACCOUNT_ID)
      .name("Savings account")
      .description("Some description")
      .currency(Currency.PLN)
      .balance(ONE_DECIMAL_PLACE_VALUE)
      .build();
  public static final AccountDTO SAVINGS_ACCOUNT_DTO = SavingsAccountDTO.builder()
      .accountId(ACCOUNT_ID)
      .name("Savings account")
      .description("Some description")
      .currency(Currency.PLN.name())
      .balance(501.10)
      .build();

  // Errors
  public static final Throwable THROWABLE = new Throwable("throwable message");
  public static final Error ERROR_UNAUTHORIZED = new Error(Error.ErrorCategory.UNAUTHORIZED,
      "UNAUTHORIZED ERROR", THROWABLE);
  public static final Error ERROR_NOT_FOUND = new Error(Error.ErrorCategory.NOT_FOUND,
      "NOT_FOUND ERROR", THROWABLE);
  public static final Error ERROR_ILLEGAL_ARGUMENT = new Error(Error.ErrorCategory.ILLEGAL_ARGUMENT,
      "ILLEGAL_ARGUMENT ERROR", THROWABLE);
  public static final Error ERROR_CONFLICT = new Error(Error.ErrorCategory.CONFLICT,
      "CONFLICT ERROR", THROWABLE);
  public static final Error ERROR_PROCESSING = new Error(Error.ErrorCategory.PROCESSING_ERROR,
      "PROCESSING ERROR", THROWABLE);
  public static final RuntimeException RUNTIME_EXCEPTION = new RuntimeException("Runtime message");

}
