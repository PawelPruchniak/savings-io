package pp.pl.io.savings.account;

import io.vavr.collection.List;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pp.pl.io.savings.account.balance.BalanceService;
import pp.pl.io.savings.account.balance.CurrencyService;
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
  AccountRepository accountRepository;
  private final CurrencyService currencyService = new CurrencyService();
  @Spy
  private final BalanceService balanceService = new BalanceService(currencyService);
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
  void shouldReturnProcessingErrorWhenFetchUserAccountIsFailure() {
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
  void shouldReturnNotFoundErrorWhenFetchUserAccountIsEmpty() {
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
  void shouldReturnProcessingErrorWhenFetchAccountsIsFailure() {
    when(savingsSecurityService.getUserId())
        .thenReturn(SOME_USER_ID);
    when(userAccountRepository.fetchUserAccount(any()))
        .thenReturn(Try.of(() -> Option.of(USER_ACCOUNT_PLN_WITHOUT_ACCOUNTS)));
    when(accountRepository.fetchAccounts(any()))
        .thenReturn(Try.failure(SOME_PROCESSING_ERROR));

    val result = userAccountService.getUserAccount();

    assertEquals(
        Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, "Cannot get related accounts")),
        result
    );
  }

  @Test
  void shouldReturnUserAccountWithEmptyAccountListSuccessfully() {
    when(savingsSecurityService.getUserId())
        .thenReturn(SOME_USER_ID);
    when(userAccountRepository.fetchUserAccount(any()))
        .thenReturn(Try.of(() -> Option.of(USER_ACCOUNT_PLN_WITHOUT_ACCOUNTS)));
    when(accountRepository.fetchAccounts(any()))
        .thenReturn(Try.of(List::empty));

    val result = userAccountService.getUserAccount();

    assertEquals(
        Either.right(USER_ACCOUNT_PLN_WITHOUT_ACCOUNTS),
        result
    );
  }

  @Test
  void shouldReturnUserAccountSuccessfully() {
    when(savingsSecurityService.getUserId())
        .thenReturn(SOME_USER_ID);
    when(userAccountRepository.fetchUserAccount(any()))
        .thenReturn(Try.of(() -> Option.of(USER_ACCOUNT_PLN_WITHOUT_ACCOUNTS)));
    when(accountRepository.fetchAccounts(any()))
        .thenReturn(Try.of(() -> ACCOUNTS));

    val result = userAccountService.getUserAccount();

    assertEquals(
        Either.right(USER_ACCOUNT_PLN),
        result
    );
  }

}
