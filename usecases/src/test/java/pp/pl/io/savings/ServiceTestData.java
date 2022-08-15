package pp.pl.io.savings;

import io.vavr.collection.List;
import pp.pl.io.savings.account.Account;
import pp.pl.io.savings.account.Currency;
import pp.pl.io.savings.account.SavingsAccount;
import pp.pl.io.savings.account.UserAccount;

import java.math.BigDecimal;

public class ServiceTestData {

  // User
  public static final String SOME_USER_ID = "USER_1";


  // Account
  private static final BigDecimal ONE_DECIMAL_PLACE_VALUE = BigDecimal.valueOf(501.1);
  public static final BigDecimal SAVINGS_ACCOUNT_PLN_VALUE = BigDecimal.valueOf(111.11);
  public static final BigDecimal SAVINGS_ACCOUNT_USD_VALUE = BigDecimal.valueOf(100.00);
  public static final String ACCOUNT_ID = "1";
  public static final Account SAVINGS_ACCOUNT = SavingsAccount.builder()
      .accountId(ACCOUNT_ID)
      .name("Savings account")
      .description("Some description")
      .currency(Currency.PLN)
      .balance(ONE_DECIMAL_PLACE_VALUE)
      .build();
  public static final Account SAVINGS_ACCOUNT_PLN = SavingsAccount.builder()
      .accountId(ACCOUNT_ID)
      .name("Savings account")
      .description("Some description")
      .currency(Currency.PLN)
      .balance(SAVINGS_ACCOUNT_PLN_VALUE)
      .build();
  public static final Account SAVINGS_ACCOUNT_USD = SavingsAccount.builder()
      .accountId(ACCOUNT_ID)
      .name("Savings account")
      .description("Some description")
      .currency(Currency.PLN)
      .balance(SAVINGS_ACCOUNT_USD_VALUE)
      .build();


  // User Account
  private static final BigDecimal ZERO_VALUE = BigDecimal.ZERO;
  public static final UserAccount USER_ACCOUNT_PLN_WITHOUT_ACCOUNTS = UserAccount.builder()
      .accounts(List.empty())
      .currency(Currency.valueOf("PLN"))
      .totalBalance(ZERO_VALUE)
      .build();

  public static final List<Account> ACCOUNTS = List.of(SAVINGS_ACCOUNT);
  public static final UserAccount USER_ACCOUNT_PLN = UserAccount.builder()
      .accounts(ACCOUNTS)
      .currency(Currency.valueOf("PLN"))
      .totalBalance(ZERO_VALUE)
      .build();


  // Errors
  public static final RuntimeException SOME_PROCESSING_ERROR = new RuntimeException("Some processing error");
}
