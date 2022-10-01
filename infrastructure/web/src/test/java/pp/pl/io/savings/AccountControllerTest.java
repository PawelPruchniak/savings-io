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
  void shouldReturnProcessingErrorForGetAccount() {
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
  void shouldReturnIllegalArgumentErrorForGetAccount() {
    // given
    when(accountService.getAccount(any()))
        .thenReturn(Either.left(ERROR_ILLEGAL_ARGUMENT));

    // when, then
    var exception =
        assertThrows(ResponseStatusException.class, () -> accountController.getAccount(SAVINGS_ACCOUNT_ID.code));
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    assertEquals(ERROR_ILLEGAL_ARGUMENT.getMessage(), exception.getReason());
    assertEquals(ERROR_ILLEGAL_ARGUMENT.getCause(), exception.getCause());
  }

  @Test
  void shouldReturnNotFoundErrorForGetAccount() {
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
  void shouldReturnInvestmentAccountDTOSuccessfully() {
    // given
    when(accountService.getAccount(INVESTMENT_ACCOUNT_ID.code))
        .thenReturn(Either.right(INVESTMENT_ACCOUNT));

    // when
    val result = accountController.getAccount(INVESTMENT_ACCOUNT_ID.code);

    // then
    assertThat(result).isEqualTo(INVESTMENT_ACCOUNT_DTO);
  }

  @Test
  void shouldReturnProcessingErrorForDeleteAccount() {
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
  void shouldReturnIllegalArgumentErrorForDeleteAccount() {
    // given
    when(accountService.deleteAccount(any()))
        .thenReturn(Either.left(ERROR_ILLEGAL_ARGUMENT));

    // when, then
    var exception =
        assertThrows(ResponseStatusException.class, () -> accountController.deleteAccount(SAVINGS_ACCOUNT_ID.code));
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    assertEquals(ERROR_ILLEGAL_ARGUMENT.getMessage(), exception.getReason());
    assertEquals(ERROR_ILLEGAL_ARGUMENT.getCause(), exception.getCause());
  }

  @Test
  void shouldReturnNotFoundErrorForDeleteAccount() {
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
  void shouldReturnNoContentForDeleteAccountSuccessfully() {
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

  @Test
  void shouldReturnProcessingErrorWhenRequestIsNullForCreateAccount() {
    assertThrows(NullPointerException.class, () -> accountController.createAccount(null));
  }

  @Test
  void shouldReturnProcessingErrorForCreateAccount() {
    // given
    when(accountService.createAccount(any()))
        .thenReturn(Either.left(ERROR_PROCESSING));

    // when, then
    var exception =
        assertThrows(ResponseStatusException.class, () -> accountController.createAccount(SAVINGS_ACCOUNT_REQUEST));
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
    assertEquals(ERROR_PROCESSING.getMessage(), exception.getReason());
    assertEquals(ERROR_PROCESSING.getCause(), exception.getCause());
  }

  @Test
  void shouldReturnIllegalArgumentErrorForCreateAccount() {
    // given
    when(accountService.createAccount(any()))
        .thenReturn(Either.left(ERROR_ILLEGAL_ARGUMENT));

    // when, then
    var exception =
        assertThrows(ResponseStatusException.class, () -> accountController.createAccount(SAVINGS_ACCOUNT_REQUEST));
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    assertEquals(ERROR_ILLEGAL_ARGUMENT.getMessage(), exception.getReason());
    assertEquals(ERROR_ILLEGAL_ARGUMENT.getCause(), exception.getCause());
  }

  @Test
  void shouldReturnCreatedForCreateAccountSuccessfully() {
    // given
    when(accountService.createAccount(any()))
        .thenReturn(Either.right(CREATED_SAVINGS_ACCOUNT_ID));

    // when
    val result = accountController.createAccount(SAVINGS_ACCOUNT_REQUEST);

    // then
    assertThat(result)
        .isEqualTo(new ResponseEntity<>(CREATED_SAVINGS_ACCOUNT_ID.code, HttpStatus.CREATED));
  }

  @Test
  void shouldReturnProcessingErrorWhenRequestIsNullForUpdateAccount() {
    assertThrows(NullPointerException.class, () -> accountController.updateAccount(null));
  }

  @Test
  void shouldReturnBadRequestWhenAccountIdIsInvalidForUpdateAccount() {
    var exception =
        assertThrows(ResponseStatusException.class, () -> accountController.updateAccount(SAVINGS_ACCOUNT_INVALID_ID_UPDATE_REQUEST));
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    assertEquals("Account Id is invalid", exception.getReason());
  }

  @Test
  void shouldReturnProcessingErrorForUpdateAccount() {
    // given
    when(accountService.updateAccount(any()))
        .thenReturn(Either.left(ERROR_PROCESSING));

    // when, then
    var exception =
        assertThrows(ResponseStatusException.class, () -> accountController.updateAccount(SAVINGS_ACCOUNT_UPDATE_REQUEST));
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
    assertEquals(ERROR_PROCESSING.getMessage(), exception.getReason());
    assertEquals(ERROR_PROCESSING.getCause(), exception.getCause());
  }

  @Test
  void shouldReturnIllegalArgumentErrorForUpdateAccount() {
    // given
    when(accountService.updateAccount(any()))
        .thenReturn(Either.left(ERROR_ILLEGAL_ARGUMENT));

    // when, then
    var exception =
        assertThrows(ResponseStatusException.class, () -> accountController.updateAccount(SAVINGS_ACCOUNT_UPDATE_REQUEST));
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    assertEquals(ERROR_ILLEGAL_ARGUMENT.getMessage(), exception.getReason());
    assertEquals(ERROR_ILLEGAL_ARGUMENT.getCause(), exception.getCause());
  }

  @Test
  void shouldReturnOkForUpdateAccountSuccessfully() {
    // given
    when(accountService.updateAccount(any()))
        .thenReturn(Either.right(null));

    // when
    val result = accountController.updateAccount(SAVINGS_ACCOUNT_UPDATE_REQUEST);

    // then
    assertThat(result)
        .isEqualTo(new ResponseEntity<>("Account with id: " + UPDATED_SAVINGS_ACCOUNT_ID.code + " was successfully updated",
            HttpStatus.OK));
  }
}
