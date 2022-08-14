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

  @Mock
  UserAccountRepository userAccountRepository;
  @Mock
  SavingsSecurityService savingsSecurityService;
  @InjectMocks
  UserAccountService userAccountService;

  @Test
  void shouldReturnProcessingError() {
    when(savingsSecurityService.getUserId())
        .thenThrow(SOME_PROCESSING_ERROR);

    val result = userAccountService.getUserAccount();

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, SOME_PROCESSING_ERROR)),
        result
    );
  }

  @Test
  void shouldReturnProcessingErrorWhenUserIdIsNull() {
    when(savingsSecurityService.getUserId())
        .thenReturn(null);

    val result = userAccountService.getUserAccount();

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, "Cannot compute user")),
        result
    );
  }

  @Test
  void shouldReturnProcessingErrorWhenUsernameIsBlank() {
    when(savingsSecurityService.getUserId())
        .thenReturn("");

    val result = userAccountService.getUserAccount();

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, "Cannot compute user")),
        result
    );
  }

  @Test
  void shouldReturnProcessingErrorWhenResultIsFailure() {
    when(savingsSecurityService.getUserId())
        .thenReturn(SOME_USER_ID);
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
    when(savingsSecurityService.getUserId())
        .thenReturn(SOME_USER_ID);
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
    when(savingsSecurityService.getUserId())
        .thenReturn(SOME_USER_ID);
    when(userAccountRepository.fetchUserAccount(any()))
        .thenReturn(Try.of(() -> Option.of(USER_ACCOUNT_PLN)));

    val result = userAccountService.getUserAccount();

    assertEquals(
        Either.right(USER_ACCOUNT_PLN),
        result
    );
  }

}
