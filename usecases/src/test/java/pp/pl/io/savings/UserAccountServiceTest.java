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
import pp.pl.io.savings.account.UserAccountRepository;
import pp.pl.io.savings.exception.Error;
import pp.pl.io.savings.organisation.SavingsSecurityService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static pp.pl.io.savings.ServiceTestData.*;

@ExtendWith(MockitoExtension.class)
class UserAccountServiceTest {

  @InjectMocks
  UserAccountService userAccountService;

  @Mock
  UserAccountRepository userAccountRepository;

  @Mock
  SavingsSecurityService savingsSecurityService;

  @Test
  void shouldReturnProcessingError() {
    when(savingsSecurityService.getUsername())
        .thenThrow(SOME_PROCESSING_ERROR);

    val result = userAccountService.getUserAccount();

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, SOME_PROCESSING_ERROR)),
        result
    );
  }

  @Test
  void shouldReturnProcessingErrorWhenUsernameIsNull() {
    when(savingsSecurityService.getUsername())
        .thenReturn(null);

    val result = userAccountService.getUserAccount();

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, "Cannot compute user")),
        result
    );
  }

  @Test
  void shouldReturnProcessingErrorWhenUsernameIsBlank() {
    when(savingsSecurityService.getUsername())
        .thenReturn("");

    val result = userAccountService.getUserAccount();

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, "Cannot compute user")),
        result
    );
  }

  @Test
  void shouldReturnProcessingErrorWhenResultIsFailure() {
    when(savingsSecurityService.getUsername())
        .thenReturn(SOME_USERNAME);
    when(userAccountRepository.fetchUserAccount(any()))
        .thenReturn(Try.failure(SOME_PROCESSING_ERROR));

    val result = userAccountService.getUserAccount();

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, "Cannot get user account")),
        result
    );
  }

  @Test
  void shouldReturnNotFoundErrorWhenResultIsEmpty() {
    when(savingsSecurityService.getUsername())
        .thenReturn(SOME_USERNAME);
    when(userAccountRepository.fetchUserAccount(any()))
        .thenReturn(Try.of(Option::none));

    val result = userAccountService.getUserAccount();

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.NOT_FOUND, "User account not found")),
        result
    );
  }

  @Test
  void shouldReturnUserAccountSuccessfully() {
    when(savingsSecurityService.getUsername())
        .thenReturn(SOME_USERNAME);
    when(userAccountRepository.fetchUserAccount(any()))
        .thenReturn(Try.of(() -> Option.of(USER_ACCOUNT_PLN)));

    val result = userAccountService.getUserAccount();

    assertEquals(
        Either.right(USER_ACCOUNT_PLN),
        result
    );
  }

}
