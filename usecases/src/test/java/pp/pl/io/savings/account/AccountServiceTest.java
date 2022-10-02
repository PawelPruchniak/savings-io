package pp.pl.io.savings.account;

import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pp.pl.io.savings.account.asset.Currency;
import pp.pl.io.savings.account.balance.BalanceService;
import pp.pl.io.savings.account.create.NewAccount;
import pp.pl.io.savings.account.id.UuidService;
import pp.pl.io.savings.exception.Error;
import pp.pl.io.savings.organisation.SavingsSecurityService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static pp.pl.io.savings.ServiceTestData.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

  @Mock
  private AccountRepository accountRepository;
  @Mock
  private SavingsSecurityService savingsSecurityService;
  @Mock
  private UuidService uuidService;
  @Mock
  private BalanceService balanceService;
  @InjectMocks
  private AccountService accountService;

  @Test
  void shouldReturnProcessingErrorForGetAccount() {
    when(savingsSecurityService.getUserId())
        .thenThrow(SOME_PROCESSING_ERROR);

    val result = accountService.getAccount(ACCOUNT_ID.code);

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, SOME_PROCESSING_ERROR)),
        result
    );
  }

  @Test
  void shouldReturnIllegalArgumentErrorWhenAccountIdIsNullForGetAccount() {
    val result = accountService.getAccount(null);

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.ILLEGAL_ARGUMENT, "Account Id is invalid")),
        result
    );
  }

  @Test
  void shouldReturnIllegalArgumentErrorWhenAccountIdIsBlankForGetAccount() {
    val result = accountService.getAccount("");

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.ILLEGAL_ARGUMENT, "Account Id is invalid")),
        result
    );
  }

  @Test
  void shouldReturnIllegalArgumentErrorWhenAccountIdIsInvalidForGetAccount() {
    val result = accountService.getAccount(INVALID_ACCOUNT_ID);

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.ILLEGAL_ARGUMENT, "Account Id is invalid")),
        result
    );
  }

  @Test
  void shouldReturnProcessingErrorWhenUserIdIsNullForGetAccount() {
    when(savingsSecurityService.getUserId())
        .thenReturn(null);

    val result = accountService.getAccount(ACCOUNT_ID.code);

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, "Cannot compute user")),
        result
    );
  }

  @Test
  void shouldReturnProcessingErrorWhenFetchResultIsFailure() {
    when(savingsSecurityService.getUserId())
        .thenReturn(SOME_USER_ID);
    when(accountRepository.fetchAccount(ACCOUNT_ID, SOME_USER_ID))
        .thenReturn(Try.failure(SOME_PROCESSING_ERROR));

    val result = accountService.getAccount(ACCOUNT_ID.code);

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, "Cannot fetch account with id: " + ACCOUNT_ID)),
        result
    );
  }

  @Test
  void shouldReturnNotFoundErrorWhenResultIsEmptyForGetAccount() {
    when(savingsSecurityService.getUserId())
        .thenReturn(SOME_USER_ID);
    when(accountRepository.fetchAccount(ACCOUNT_ID, SOME_USER_ID))
        .thenReturn(Try.of(Option::none));

    val result = accountService.getAccount(ACCOUNT_ID.code);

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.NOT_FOUND, "Account with id: " + ACCOUNT_ID + " not found")),
        result
    );
  }

  @Test
  void shouldReturnSavingsAccountSuccessfully() {
    when(savingsSecurityService.getUserId())
        .thenReturn(SOME_USER_ID);
    when(accountRepository.fetchAccount(ACCOUNT_ID, SOME_USER_ID))
        .thenReturn(Try.of(() -> Option.of(SAVINGS_ACCOUNT)));

    val result = accountService.getAccount(ACCOUNT_ID.code);

    assertEquals(
        Either.right(SAVINGS_ACCOUNT),
        result
    );
  }

  @Test
  void shouldReturnInvestmentAccountSuccessfully() {
    when(savingsSecurityService.getUserId())
        .thenReturn(SOME_USER_ID);
    when(accountRepository.fetchAccount(ACCOUNT_ID, SOME_USER_ID))
        .thenReturn(Try.of(() -> Option.of(INVESTMENT_ACCOUNT_GPW_PLN)));
    when(balanceService.calculateBalance(INVESTMENT_ACCOUNT_GPW_PLN, Currency.PLN))
        .thenReturn(INVESTMENT_ACCOUNT_PLN_INVEST_RESULT);

    val result = accountService.getAccount(ACCOUNT_ID.code);

    //todo: change this test
    assertEquals(
        Either.right(INVESTMENT_ACCOUNT_GPW_PLN_RECALCULATED),
        result
    );
  }

  @Test
  void shouldReturnProcessingErrorForDeleteAccount() {
    when(savingsSecurityService.getUserId())
        .thenReturn(SOME_USER_ID);
    when(accountRepository.fetchAccount(ACCOUNT_ID, SOME_USER_ID))
        .thenReturn(Try.of(() -> Option.of(SAVINGS_ACCOUNT)));
    when(accountRepository.deleteAccount(SAVINGS_ACCOUNT))
        .thenThrow(SOME_PROCESSING_ERROR);

    val result = accountService.deleteAccount(ACCOUNT_ID.code);

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, SOME_PROCESSING_ERROR)),
        result
    );
  }

  @Test
  void shouldReturnIllegalArgumentErrorWhenAccountIdIsNullForDeleteAccount() {
    val result = accountService.deleteAccount(null);

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.ILLEGAL_ARGUMENT, "Account Id is invalid")),
        result
    );
  }

  @Test
  void shouldReturnIllegalArgumentErrorWhenAccountIdIsBlankForDeleteAccount() {
    val result = accountService.deleteAccount("");

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.ILLEGAL_ARGUMENT, "Account Id is invalid")),
        result
    );
  }

  @Test
  void shouldReturnProcessingErrorWhenUserIdIsNullForDeleteAccount() {
    when(savingsSecurityService.getUserId())
        .thenReturn(null);

    val result = accountService.deleteAccount(ACCOUNT_ID.code);

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, "Cannot compute user")),
        result
    );
  }

  @Test
  void shouldReturnProcessingErrorWhenGetAccountIsFailureForDeleteAccount() {
    when(savingsSecurityService.getUserId())
        .thenReturn(SOME_USER_ID);
    when(accountRepository.fetchAccount(ACCOUNT_ID, SOME_USER_ID))
        .thenReturn(Try.failure(SOME_PROCESSING_ERROR));

    val result = accountService.deleteAccount(ACCOUNT_ID.code);

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, "Cannot fetch account with id: " + ACCOUNT_ID)),
        result
    );
  }

  @Test
  void shouldReturnNotFoundErrorWhenAccountIsEmptyForDeleteAccount() {
    when(savingsSecurityService.getUserId())
        .thenReturn(SOME_USER_ID);
    when(accountRepository.fetchAccount(ACCOUNT_ID, SOME_USER_ID))
        .thenReturn(Try.of(Option::none));

    val result = accountService.deleteAccount(ACCOUNT_ID.code);

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.NOT_FOUND, "Account with id: " + ACCOUNT_ID + " not found")),
        result
    );
  }

  @Test
  void shouldReturnProcessingErrorWhenDeleteResultIsFailure() {
    when(savingsSecurityService.getUserId())
        .thenReturn(SOME_USER_ID);
    when(accountRepository.fetchAccount(ACCOUNT_ID, SOME_USER_ID))
        .thenReturn(Try.of(() -> Option.of(SAVINGS_ACCOUNT)));
    when(accountRepository.deleteAccount(SAVINGS_ACCOUNT))
        .thenReturn(Try.failure(SOME_PROCESSING_ERROR));

    val result = accountService.deleteAccount(ACCOUNT_ID.code);

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, "Cannot delete account with id: " + ACCOUNT_ID)),
        result
    );
  }

  @Test
  void shouldDeleteSavingsAccountSuccessfully() {
    when(savingsSecurityService.getUserId())
        .thenReturn(SOME_USER_ID);
    when(accountRepository.fetchAccount(ACCOUNT_ID, SOME_USER_ID))
        .thenReturn(Try.of(() -> Option.of(SAVINGS_ACCOUNT)));
    when(accountRepository.deleteAccount(SAVINGS_ACCOUNT))
        .thenReturn(Try.success(null));

    val result = accountService.deleteAccount(ACCOUNT_ID.code);

    assertEquals(
        Either.right(null),
        result
    );
  }

  //todo: create test for delete Investment Account

  @Test
  void shouldReturnIllegalArgumentErrorWhenAccountCommandIsNullForCreateAccount() {
    val result = accountService.createAccount(null);

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.ILLEGAL_ARGUMENT, "Account command cannot be null")),
        result
    );
  }

  @Test
  void shouldReturnProcessingErrorForCreateAccount() {
    when(savingsSecurityService.getUserId())
        .thenThrow(SOME_PROCESSING_ERROR);

    val result = accountService.createAccount(SAVINGS_ACCOUNT_COMMAND);

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, SOME_PROCESSING_ERROR)),
        result
    );
  }

  @Test
  void shouldReturnProcessingErrorWhenUserIdIsNullForCreateAccount() {
    when(savingsSecurityService.getUserId())
        .thenReturn(null);

    val result = accountService.createAccount(SAVINGS_ACCOUNT_COMMAND);

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, "Cannot compute user")),
        result
    );
  }

  @Test
  void shouldReturnProcessingErrorWhenCreateResultIsFailure() {
    when(savingsSecurityService.getUserId())
        .thenReturn(SOME_USER_ID);
    when(uuidService.createRandomAccountId())
        .thenReturn(ACCOUNT_ID);
    when(accountRepository.createAccount(new NewAccount(SOME_USER_ID, ACCOUNT_ID, SAVINGS_ACCOUNT_COMMAND)))
        .thenReturn(Try.failure(SOME_PROCESSING_ERROR));

    val result = accountService.createAccount(SAVINGS_ACCOUNT_COMMAND);

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, "Cannot create account with command: " + SAVINGS_ACCOUNT_COMMAND)),
        result
    );
  }

  @Test
  void shouldCreateSavingsAccountSuccessfully() {
    when(savingsSecurityService.getUserId())
        .thenReturn(SOME_USER_ID);
    when(uuidService.createRandomAccountId())
        .thenReturn(ACCOUNT_ID);
    when(accountRepository.createAccount(new NewAccount(SOME_USER_ID, ACCOUNT_ID, SAVINGS_ACCOUNT_COMMAND)))
        .thenReturn(Try.success(null));

    val result = accountService.createAccount(SAVINGS_ACCOUNT_COMMAND);

    assertEquals(
        Either.right(ACCOUNT_ID),
        result
    );
  }

  //todo: create test for create Investment Account

  @Test
  void shouldReturnProcessingErrorForUpdateAccount() {
    when(savingsSecurityService.getUserId())
        .thenReturn(SOME_USER_ID);
    when(accountRepository.fetchAccount(ACCOUNT_ID, SOME_USER_ID))
        .thenReturn(Try.of(() -> Option.of(SAVINGS_ACCOUNT)));
    when(accountRepository.updateAccount(SAVINGS_ACCOUNT_UPDATE_COMMAND))
        .thenThrow(SOME_PROCESSING_ERROR);

    val result = accountService.updateAccount(SAVINGS_ACCOUNT_UPDATE_COMMAND);

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, SOME_PROCESSING_ERROR)),
        result
    );
  }

  @Test
  void shouldReturnIllegalArgumentErrorWhenCommandIsNullForUpdateAccount() {
    val result = accountService.updateAccount(null);

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.ILLEGAL_ARGUMENT, "Account update command cannot be null")),
        result
    );
  }

  @Test
  void shouldReturnProcessingErrorWhenUserIdIsNullForUpdateAccount() {
    when(savingsSecurityService.getUserId())
        .thenReturn(null);

    val result = accountService.updateAccount(SAVINGS_ACCOUNT_UPDATE_COMMAND);

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, "Cannot compute user")),
        result
    );
  }

  @Test
  void shouldReturnProcessingErrorWhenGetAccountIsFailureForUpdateAccount() {
    when(savingsSecurityService.getUserId())
        .thenReturn(SOME_USER_ID);
    when(accountRepository.fetchAccount(ACCOUNT_ID, SOME_USER_ID))
        .thenReturn(Try.failure(SOME_PROCESSING_ERROR));

    val result = accountService.updateAccount(SAVINGS_ACCOUNT_UPDATE_COMMAND);

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, "Cannot fetch account with id: " + ACCOUNT_ID)),
        result
    );
  }

  @Test
  void shouldReturnNotFoundErrorWhenAccountIsEmptyForUpdateAccount() {
    when(savingsSecurityService.getUserId())
        .thenReturn(SOME_USER_ID);
    when(accountRepository.fetchAccount(ACCOUNT_ID, SOME_USER_ID))
        .thenReturn(Try.of(Option::none));

    val result = accountService.updateAccount(SAVINGS_ACCOUNT_UPDATE_COMMAND);

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.NOT_FOUND, "Account with id: " + ACCOUNT_ID + " not found")),
        result
    );
  }

  @Test
  void shouldReturnProcessingErrorWhenUpdateResultIsFailure() {
    when(savingsSecurityService.getUserId())
        .thenReturn(SOME_USER_ID);
    when(accountRepository.fetchAccount(ACCOUNT_ID, SOME_USER_ID))
        .thenReturn(Try.of(() -> Option.of(SAVINGS_ACCOUNT)));
    when(accountRepository.updateAccount(SAVINGS_ACCOUNT_UPDATE_COMMAND))
        .thenReturn(Try.failure(SOME_PROCESSING_ERROR));

    val result = accountService.updateAccount(SAVINGS_ACCOUNT_UPDATE_COMMAND);

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR,
            "Cannot update account with command: " + SAVINGS_ACCOUNT_UPDATE_COMMAND)),
        result
    );
  }

  @Test
  void shouldUpdateSavingsAccountSuccessfully() {
    when(savingsSecurityService.getUserId())
        .thenReturn(SOME_USER_ID);
    when(accountRepository.fetchAccount(ACCOUNT_ID, SOME_USER_ID))
        .thenReturn(Try.of(() -> Option.of(SAVINGS_ACCOUNT)));
    when(accountRepository.updateAccount(SAVINGS_ACCOUNT_UPDATE_COMMAND))
        .thenReturn(Try.success(null));

    val result = accountService.updateAccount(SAVINGS_ACCOUNT_UPDATE_COMMAND);

    assertEquals(
        Either.right(null),
        result
    );
  }

  //todo: create test for update Investment Account
}
