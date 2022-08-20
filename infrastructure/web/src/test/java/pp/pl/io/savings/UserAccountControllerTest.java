package pp.pl.io.savings;

import io.vavr.control.Either;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import pp.pl.io.savings.account.UserAccountService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static pp.pl.io.savings.AccountTestData.*;


@ExtendWith(MockitoExtension.class)
class UserAccountControllerTest {

  @Mock
  UserAccountService userAccountService;
  @InjectMocks
  UserAccountController userAccountController;

  @Test
  void shouldReturnProcessingError() {
    // given
    when(userAccountService.getUserAccount())
        .thenReturn(Either.left(ERROR_PROCESSING));

    // when, then
    assertThrows(ResponseStatusException.class, () -> userAccountController.getUserAccount());
  }

  @Test
  void shouldReturnUnauthorizedError() {
    // given
    when(userAccountService.getUserAccount())
        .thenReturn(Either.left(ERROR_UNAUTHORIZED));

    // when, then
    assertThrows(ResponseStatusException.class, () -> userAccountController.getUserAccount());
  }


  @Test
  void shouldReturnUserAccountDTOSuccessfully() {
    // given
    when(userAccountService.getUserAccount())
        .thenReturn(Either.right(USER_ACCOUNT_PLN));

    // when
    val result = userAccountController.getUserAccount();

    // then
    assertThat(result).isEqualTo(USER_ACCOUNT_PLN_DTO);
  }
}
