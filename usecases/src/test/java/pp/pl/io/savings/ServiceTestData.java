package pp.pl.io.savings;

import io.vavr.collection.List;
import pp.pl.io.savings.account.*;
import pp.pl.io.savings.account.create.AccountCommand;
import pp.pl.io.savings.account.create.SavingsAccountCommand;
import pp.pl.io.savings.organisation.UserId;

import java.math.BigDecimal;

public class ServiceTestData {

  // User
  public static final UserId SOME_USER_ID = UserId.of("USER_1");


  // Account
  private static final BigDecimal ONE_DECIMAL_PLACE_VALUE = BigDecimal.valueOf(501.1);
  public static final BigDecimal SAVINGS_ACCOUNT_PLN_VALUE = BigDecimal.valueOf(111.11);
  public static final BigDecimal SAVINGS_ACCOUNT_USD_VALUE = BigDecimal.valueOf(100.00);
  public static final BigDecimal SAVINGS_ACCOUNT_EUR_VALUE = BigDecimal.valueOf(176.22);
  public static final AccountId ACCOUNT_ID = AccountId.of("00000001-e89b-42d3-a456-556642440000");
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
      .currency(Currency.USD)
      .balance(SAVINGS_ACCOUNT_USD_VALUE)
      .build();

  public static final AccountCommand SAVINGS_ACCOUNT_COMMAND = SavingsAccountCommand.builder()
      .name("Savings account")
      .description("Savings account description")
      .currency(Currency.EUR)
      .balance(SAVINGS_ACCOUNT_EUR_VALUE.doubleValue())
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
      .totalBalance(ONE_DECIMAL_PLACE_VALUE)
      .build();


  // Errors
  public static final RuntimeException SOME_PROCESSING_ERROR = new RuntimeException("Some processing error");
}
