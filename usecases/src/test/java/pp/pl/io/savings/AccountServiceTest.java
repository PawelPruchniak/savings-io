package pp.pl.io.savings;

import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pp.pl.io.savings.account.AccountRepository;
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
  void shouldReturnProcessingError() {
    when(savingsSecurityService.getUsername())
        .thenThrow(SOME_PROCESSING_ERROR);

    val result = accountService.getAccount(ACCOUNT_ID);

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, SOME_PROCESSING_ERROR)),
        result
    );
  }

  @Test
  void shouldReturnProcessingErrorWhenAccountIdIsNull() {
    val result = accountService.getAccount(null);

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, "Account id cannot be blank")),
        result
    );
  }

  @Test
  void shouldReturnProcessingErrorWhenAccountIdIsBlank() {
    val result = accountService.getAccount("");

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, "Account id cannot be blank")),
        result
    );
  }

  @Test
  void shouldReturnProcessingErrorWhenUsernameIsNull() {
    when(savingsSecurityService.getUsername())
        .thenReturn(null);

    val result = accountService.getAccount(ACCOUNT_ID);

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, "Cannot compute user")),
        result
    );
  }

  @Test
  void shouldReturnProcessingErrorWhenUsernameIsBlank() {
    when(savingsSecurityService.getUsername())
        .thenReturn("");

    val result = accountService.getAccount(ACCOUNT_ID);

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, "Cannot compute user")),
        result
    );
  }

  @Test
  void shouldReturnProcessingErrorWhenResultIsFailure() {
    when(savingsSecurityService.getUsername())
        .thenReturn(SOME_USERNAME);
    when(accountRepository.fetchAccount(ACCOUNT_ID, SOME_USERNAME))
        .thenReturn(Try.failure(SOME_PROCESSING_ERROR));

    val result = accountService.getAccount(ACCOUNT_ID);

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, "Cannot get account with id: " + ACCOUNT_ID)),
        result
    );
  }

  @Test
  void shouldReturnNotFoundErrorWhenResultIsEmpty() {
    when(savingsSecurityService.getUsername())
        .thenReturn(SOME_USERNAME);
    when(accountRepository.fetchAccount(ACCOUNT_ID, SOME_USERNAME))
        .thenReturn(Try.of(Option::none));

    val result = accountService.getAccount(ACCOUNT_ID);

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.NOT_FOUND, "Account with id: " + ACCOUNT_ID + " not found")),
        result
    );
  }

  @Test
  void shouldReturnSavingsAccountSuccessfully() {
    when(savingsSecurityService.getUsername())
        .thenReturn(SOME_USERNAME);
    when(accountRepository.fetchAccount(ACCOUNT_ID, SOME_USERNAME))
        .thenReturn(Try.of(() -> Option.of(SAVINGS_ACCOUNT)));

    val result = accountService.getAccount(ACCOUNT_ID);

    assertEquals(
        Either.right(SAVINGS_ACCOUNT),
        result
    );
  }
}