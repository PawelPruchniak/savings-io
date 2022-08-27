package pp.pl.io.savings;

import io.vavr.control.Either;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import pp.pl.io.savings.account.AccountService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static pp.pl.io.savings.AccountTestData.*;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

  @Mock
  AccountService accountService;
  @InjectMocks
  AccountController accountController;

  @Test
  void shouldReturnProcessingErrorWhenGettingAccount() {
    // given
    when(accountService.getAccount(any()))
        .thenReturn(Either.left(ERROR_PROCESSING));

    // when, then
    var exception =
        assertThrows(ResponseStatusException.class, () -> accountController.getAccount(null));
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
    assertEquals(ERROR_PROCESSING.getMessage(), exception.getReason());
    assertEquals(ERROR_PROCESSING.getCause(), exception.getCause());
  }

  @Test
  void shouldReturnUnauthorizedErrorWhenGettingAccount() {
    // given
    when(accountService.getAccount(any()))
        .thenReturn(Either.left(ERROR_UNAUTHORIZED));

    // when, then
    var exception =
        assertThrows(ResponseStatusException.class, () -> accountController.getAccount(SAVINGS_ACCOUNT_ID.code));
    assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
    assertEquals(ERROR_UNAUTHORIZED.getMessage(), exception.getReason());
    assertEquals(ERROR_UNAUTHORIZED.getCause(), exception.getCause());
  }

  @Test
  void shouldReturnNotFoundErrorWhenGettingAccount() {
    // given
    when(accountService.getAccount(any()))
        .thenReturn(Either.left(ERROR_NOT_FOUND));

    // when, then
    var exception =
        assertThrows(ResponseStatusException.class, () -> accountController.getAccount(SAVINGS_ACCOUNT_ID.code));
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    assertEquals(ERROR_NOT_FOUND.getMessage(), exception.getReason());
    assertEquals(ERROR_NOT_FOUND.getCause(), exception.getCause());
  }

  @Test
  void shouldReturnSavingsAccountDTOSuccessfully() {
    // given
    when(accountService.getAccount(SAVINGS_ACCOUNT_ID.code))
        .thenReturn(Either.right(SAVINGS_ACCOUNT));

    // when
    val result = accountController.getAccount(SAVINGS_ACCOUNT_ID.code);

    // then
    assertThat(result).isEqualTo(SAVINGS_ACCOUNT_DTO);
  }

  @Test
  void shouldReturnProcessingErrorWhenDeletingAccount() {
    // given
    when(accountService.deleteAccount(any()))
        .thenReturn(Either.left(ERROR_PROCESSING));

    // when, then
    var exception =
        assertThrows(ResponseStatusException.class, () -> accountController.deleteAccount(null));
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
    assertEquals(ERROR_PROCESSING.getMessage(), exception.getReason());
    assertEquals(ERROR_PROCESSING.getCause(), exception.getCause());
  }

  @Test
  void shouldReturnUnauthorizedErrorWhenDeletingAccount() {
    // given
    when(accountService.deleteAccount(any()))
        .thenReturn(Either.left(ERROR_UNAUTHORIZED));

    // when, then
    var exception =
        assertThrows(ResponseStatusException.class, () -> accountController.deleteAccount(SAVINGS_ACCOUNT_ID.code));
    assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
    assertEquals(ERROR_UNAUTHORIZED.getMessage(), exception.getReason());
    assertEquals(ERROR_UNAUTHORIZED.getCause(), exception.getCause());
  }

  @Test
  void shouldReturnNotFoundErrorWhenDeletingAccount() {
    // given
    when(accountService.deleteAccount(any()))
        .thenReturn(Either.left(ERROR_NOT_FOUND));

    // when, then
    var exception =
        assertThrows(ResponseStatusException.class, () -> accountController.deleteAccount(SAVINGS_ACCOUNT_ID.code));
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    assertEquals(ERROR_NOT_FOUND.getMessage(), exception.getReason());
    assertEquals(ERROR_NOT_FOUND.getCause(), exception.getCause());
  }

  @Test
  void shouldReturnNoContentWhenDeletingAccountSuccessfully() {
    // given
    when(accountService.deleteAccount(SAVINGS_ACCOUNT_ID.code))
        .thenReturn(Either.right(null));

    // when
    val result = accountController.deleteAccount(SAVINGS_ACCOUNT_ID.code);

    // then
    assertThat(result)
        .isEqualTo(new ResponseEntity<>("Account with id: " + SAVINGS_ACCOUNT_ID.code + " was successfully deleted",
            HttpStatus.NO_CONTENT));
  }
}
