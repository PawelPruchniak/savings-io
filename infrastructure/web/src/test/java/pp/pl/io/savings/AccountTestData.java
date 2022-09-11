package pp.pl.io.savings;

import io.vavr.collection.List;
import pp.pl.io.savings.account.*;
import pp.pl.io.savings.dto.request.create.AccountRequest;
import pp.pl.io.savings.dto.request.create.SavingsAccountRequest;
import pp.pl.io.savings.dto.request.update.AccountUpdateRequest;
import pp.pl.io.savings.dto.request.update.SavingsAccountUpdateRequest;
import pp.pl.io.savings.dto.response.AccountDTO;
import pp.pl.io.savings.dto.response.SavingsAccountDTO;
import pp.pl.io.savings.dto.response.UserAccountDTO;
import pp.pl.io.savings.exception.Error;

import java.math.BigDecimal;

public class AccountTestData {

  // Account
  public static final BigDecimal ONE_DECIMAL_PLACE_VALUE = BigDecimal.valueOf(501.1);
  public static final BigDecimal TWO_DECIMAL_PLACE_VALUE = BigDecimal.valueOf(2870.82);
  public static final BigDecimal FOUR_DECIMAL_PLACE_VALUE = BigDecimal.valueOf(3.9876);
  public static final AccountId SAVINGS_ACCOUNT_ID = AccountId.of("00000001-e89b-42d3-a456-556642440000");
  public static final AccountId CREATED_SAVINGS_ACCOUNT_ID = AccountId.of("00000002-e89b-42d3-a456-556642440000");
  public static final AccountId UPDATED_SAVINGS_ACCOUNT_ID = AccountId.of("00000003-e89b-42d3-a456-556642440000");
  public static final Account SAVINGS_ACCOUNT = SavingsAccount.builder()
      .accountId(SAVINGS_ACCOUNT_ID)
      .name("Savings account")
      .description("Some description")
      .currency(Currency.PLN)
      .balance(ONE_DECIMAL_PLACE_VALUE)
      .build();
  public static final AccountDTO SAVINGS_ACCOUNT_DTO = SavingsAccountDTO.builder()
      .accountId(SAVINGS_ACCOUNT_ID.code)
      .name("Savings account")
      .description("Some description")
      .currency(Currency.PLN.name())
      .balance(501.10)
      .build();

  // Create Request
  public static final AccountRequest SAVINGS_ACCOUNT_REQUEST = SavingsAccountRequest.builder()
      .name("Savings account")
      .description("Savings account description")
      .currency(Currency.EUR.name())
      .balance(500.76)
      .build();
  public static final AccountRequest SAVINGS_ACCOUNT_MINIMUM_REQUEST = SavingsAccountRequest.builder()
      .currency(Currency.USD.name())
      .build();

  // Update Request
  public static final AccountUpdateRequest SAVINGS_ACCOUNT_UPDATE_REQUEST = SavingsAccountUpdateRequest.builder()
      .accountId(UPDATED_SAVINGS_ACCOUNT_ID.code)
      .name("Updated savings account")
      .description("Updated savings account description")
      .currency(Currency.PLN.name())
      .balance(286.66)
      .build();
  public static final AccountUpdateRequest SAVINGS_ACCOUNT_MINIMUM_UPDATE_REQUEST = SavingsAccountUpdateRequest.builder()
      .accountId(UPDATED_SAVINGS_ACCOUNT_ID.code)
      .currency(Currency.EUR.name())
      .build();
  public static final AccountUpdateRequest SAVINGS_ACCOUNT_INVALID_ID_UPDATE_REQUEST = SavingsAccountUpdateRequest.builder()
      .accountId("Some invalid id")
      .build();

  // User Account
  public static final UserAccount USER_ACCOUNT_PLN = UserAccount.builder()
      .currency(Currency.valueOf("PLN"))
      .totalBalance(ONE_DECIMAL_PLACE_VALUE)
      .accounts(List.of(SAVINGS_ACCOUNT))
      .build();
  public static final UserAccountDTO USER_ACCOUNT_PLN_DTO = UserAccountDTO.builder()
      .currency("PLN")
      .totalBalance(501.10)
      .subAccountsIds(List.of(SAVINGS_ACCOUNT.getAccountId().code).toJavaList())
      .build();
  public static final UserAccount USER_ACCOUNT_EUR = UserAccount.builder()
      .currency(Currency.valueOf("EUR"))
      .totalBalance(TWO_DECIMAL_PLACE_VALUE)
      .build();

  public static final UserAccount USER_ACCOUNT_USD = UserAccount.builder()
      .currency(Currency.valueOf("USD"))
      .totalBalance(FOUR_DECIMAL_PLACE_VALUE)
      .accounts(List.of(SAVINGS_ACCOUNT))
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
