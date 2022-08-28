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
import pp.pl.io.savings.exception.Error;
import pp.pl.io.savings.organisation.SavingsSecurityService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static pp.pl.io.savings.ServiceTestData.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

  @Mock
  AccountRepository accountRepository;
  @Mock
  SavingsSecurityService savingsSecurityService;
  @InjectMocks
  AccountService accountService;

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
  void shouldReturnProcessingErrorWhenAccountIdIsNullForGetAccount() {
    val result = accountService.getAccount(null);

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, "Account id cannot be blank")),
        result
    );
  }

  @Test
  void shouldReturnProcessingErrorWhenAccountIdIsBlankForGetAccount() {
    val result = accountService.getAccount("");

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, "Account id cannot be blank")),
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
  void shouldReturnProcessingErrorWhenResultIsFailureForGetAccount() {
    when(savingsSecurityService.getUserId())
        .thenReturn(SOME_USER_ID);
    when(accountRepository.fetchAccount(ACCOUNT_ID, SOME_USER_ID))
        .thenReturn(Try.failure(SOME_PROCESSING_ERROR));

    val result = accountService.getAccount(ACCOUNT_ID.code);

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, "Cannot get account with id: " + ACCOUNT_ID)),
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
  void shouldReturnProcessingErrorWhenAccountIdIsNullForDeleteAccount() {
    val result = accountService.deleteAccount(null);

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, "Account id cannot be blank")),
        result
    );
  }

  @Test
  void shouldReturnProcessingErrorWhenAccountIdIsBlankForDeleteAccount() {
    val result = accountService.deleteAccount("");

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, "Account id cannot be blank")),
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
        Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, "Cannot get account with id: " + ACCOUNT_ID)),
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
  void shouldReturnProcessingErrorWhenDeleteResultIsFailureForDeleteAccount() {
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
}
