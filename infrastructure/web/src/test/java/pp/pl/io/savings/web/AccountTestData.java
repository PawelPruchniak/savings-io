package pp.pl.io.savings.web;

import io.vavr.collection.List;
import pp.pl.io.savings.domain.account.*;
import pp.pl.io.savings.domain.account.asset.Currency;
import pp.pl.io.savings.domain.account.asset.Stocks;
import pp.pl.io.savings.domain.exception.Error;
import pp.pl.io.savings.web.dto.request.create.AccountRequest;
import pp.pl.io.savings.web.dto.request.create.SavingsAccountRequest;
import pp.pl.io.savings.web.dto.request.update.AccountUpdateRequest;
import pp.pl.io.savings.web.dto.request.update.SavingsAccountUpdateRequest;
import pp.pl.io.savings.web.dto.response.AccountDTO;
import pp.pl.io.savings.web.dto.response.InvestmentAccountDTO;
import pp.pl.io.savings.web.dto.response.SavingsAccountDTO;
import pp.pl.io.savings.web.dto.response.UserAccountDTO;

import java.math.BigDecimal;

public class AccountTestData {

  // Account
  public static final BigDecimal ONE_DECIMAL_PLACE_VALUE = BigDecimal.valueOf(501.1);
  public static final BigDecimal TWO_DECIMAL_PLACE_VALUE = BigDecimal.valueOf(2870.82);
  public static final BigDecimal FOUR_DECIMAL_PLACE_VALUE = BigDecimal.valueOf(3.9876);
  public static final AccountId SAVINGS_ACCOUNT_ID = AccountId.of("00000001-e89b-42d3-a456-556642440000");
  public static final AccountId CREATED_SAVINGS_ACCOUNT_ID = AccountId.of("00000002-e89b-42d3-a456-556642440000");
  public static final AccountId UPDATED_SAVINGS_ACCOUNT_ID = AccountId.of("00000003-e89b-42d3-a456-556642440000");
  public static final AccountId INVESTMENT_ACCOUNT_ID = AccountId.of("00000004-e89b-42d3-a456-556642440000");
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
      .currency(Currency.PLN.getCode())
      .balance(501.10)
      .build();

  public static final Account INVESTMENT_ACCOUNT = InvestmentAccount.builder()
      .accountId(INVESTMENT_ACCOUNT_ID)
      .name("Investment account")
      .description("Some description")
      .asset(Stocks.GPW)
      .assetQuantity(BigDecimal.valueOf(20.0))
      .currencyInvested(Currency.PLN)
      .amountInvested(FOUR_DECIMAL_PLACE_VALUE)
      .build();
  public static final AccountDTO INVESTMENT_ACCOUNT_DTO = InvestmentAccountDTO.builder()
      .accountId(INVESTMENT_ACCOUNT_ID.code)
      .name("Investment account")
      .description("Some description")
      .asset(Stocks.GPW.getCode())
      .assetQuantity(20.0)
      .currencyInvested(Currency.PLN.getCode())
      .amountInvested(3.99)
      .investmentResultValue(0.0)
      .investmentResultPercentage(0.0)
      .build();

  // Create Request
  public static final AccountRequest SAVINGS_ACCOUNT_REQUEST = SavingsAccountRequest.builder()
      .name("Savings account")
      .description("Savings account description")
      .currency(Currency.EUR.getCode())
      .balance(500.76)
      .build();
  public static final AccountRequest SAVINGS_ACCOUNT_MINIMUM_REQUEST = SavingsAccountRequest.builder()
      .name("Minimal savings account")
      .currency(Currency.USD.getCode())
      .build();

  // Update Request
  public static final AccountUpdateRequest SAVINGS_ACCOUNT_UPDATE_REQUEST = SavingsAccountUpdateRequest.builder()
      .accountId(UPDATED_SAVINGS_ACCOUNT_ID.code)
      .name("Updated savings account")
      .description("Updated savings account description")
      .currency(Currency.PLN.getCode())
      .balance(286.66)
      .build();
  public static final AccountUpdateRequest SAVINGS_ACCOUNT_MINIMUM_UPDATE_REQUEST = SavingsAccountUpdateRequest.builder()
      .name("Minimal savings account")
      .accountId(UPDATED_SAVINGS_ACCOUNT_ID.code)
      .currency(Currency.EUR.getCode())
      .build();
  public static final AccountUpdateRequest SAVINGS_ACCOUNT_INVALID_ID_UPDATE_REQUEST = SavingsAccountUpdateRequest.builder()
      .accountId("Some invalid id")
      .build();

  // User Account
  public static final UserAccount USER_ACCOUNT_PLN = UserAccount.builder()
      .currency(Currency.PLN)
      .totalBalance(ONE_DECIMAL_PLACE_VALUE)
      .accounts(List.of(SAVINGS_ACCOUNT))
      .build();
  public static final UserAccountDTO USER_ACCOUNT_PLN_DTO = UserAccountDTO.builder()
      .currency("PLN")
      .totalBalance(501.10)
      .subAccountsIds(List.of(SAVINGS_ACCOUNT.getAccountId().code).toJavaList())
      .build();
  public static final UserAccount USER_ACCOUNT_EUR = UserAccount.builder()
      .currency(Currency.EUR)
      .totalBalance(TWO_DECIMAL_PLACE_VALUE)
      .build();

  public static final UserAccount USER_ACCOUNT_USD = UserAccount.builder()
      .currency(Currency.USD)
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
