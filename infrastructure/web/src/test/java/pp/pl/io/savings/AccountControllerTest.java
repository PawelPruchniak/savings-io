package pp.pl.io.savings;

import io.vavr.control.Either;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
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
  void shouldReturnProcessingError() {
    // given
    when(accountService.getAccount(any()))
        .thenReturn(Either.left(ERROR_PROCESSING));


    // when, then
    assertThrows(ResponseStatusException.class, () -> accountController.getUserAccount(null));
  }

  @Test
  void shouldReturnUnauthorizedError() {
    // given
    when(accountService.getAccount(any()))
        .thenReturn(Either.left(ERROR_UNAUTHORIZED));

    // when, then
    assertThrows(ResponseStatusException.class, () -> accountController.getUserAccount(ACCOUNT_ID));
  }


  @Test
  void shouldReturnSavingsAccountDTOSuccessfully() {
    // given
    when(accountService.getAccount(ACCOUNT_ID))
        .thenReturn(Either.right(SAVINGS_ACCOUNT));

    // when
    val result = accountController.getUserAccount(ACCOUNT_ID);

    // then
    assertThat(result).isEqualTo(SAVINGS_ACCOUNT_DTO);
  }
}