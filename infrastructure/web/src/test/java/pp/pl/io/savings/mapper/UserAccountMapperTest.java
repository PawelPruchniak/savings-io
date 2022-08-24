package pp.pl.io.savings.mapper;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pp.pl.io.savings.AccountTestData.*;

class UserAccountMapperTest {

  @Test
  void shouldThrowWhenUserAccountIsNull() {
    assertThrows(NullPointerException.class, () -> UserAccountMapper.toUserAccountDTO(null));
  }

  @Test
  void shouldMapUserAccountPlnCorrectly() {
    val userAccountDTO = UserAccountMapper.toUserAccountDTO(USER_ACCOUNT_PLN);

    assertThat(userAccountDTO)
        .hasFieldOrPropertyWithValue("currency", "PLN")
        .hasFieldOrPropertyWithValue("totalBalance", 501.10)
        .hasFieldOrPropertyWithValue("subAccountsIds", List.of(SAVINGS_ACCOUNT_ID.code));
  }

  @Test
  void shouldMapUserAccountEurCorrectly() {
    val userAccountDTO = UserAccountMapper.toUserAccountDTO(USER_ACCOUNT_EUR);

    assertThat(userAccountDTO)
        .hasFieldOrPropertyWithValue("currency", "EUR")
        .hasFieldOrPropertyWithValue("totalBalance", 2870.82);
  }

  @Test
  void shouldMapUserAccountUsdCorrectly() {
    val userAccountDTO = UserAccountMapper.toUserAccountDTO(USER_ACCOUNT_USD);

    assertThat(userAccountDTO)
        .hasFieldOrPropertyWithValue("currency", "USD")
        .hasFieldOrPropertyWithValue("totalBalance", 3.99)
        .hasFieldOrPropertyWithValue("subAccountsIds", List.of(SAVINGS_ACCOUNT_ID.code));
  }

}
