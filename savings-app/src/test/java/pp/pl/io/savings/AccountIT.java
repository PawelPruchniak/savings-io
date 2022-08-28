package pp.pl.io.savings;


import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pp.pl.io.savings.account.Currency;
import pp.pl.io.savings.dto.response.AccountDTO;
import pp.pl.io.savings.dto.response.SavingsAccountDTO;
import pp.pl.io.savings.dto.response.UserAccountDTO;
import pp.pl.io.savings.organisation.SavingsSecurityService;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccountIT extends CommonIT {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private SavingsSecurityService savingsSecurityService;

  public static final String SAVINGS_ACCOUNT_ID_1 = "1";
  public static final String SAVINGS_ACCOUNT_TO_DELETE_ID = "2";

  @Test
  @Order(1)
  void databaseContainerShouldBeRunning() {
    POSTGRES_CONTAINER.getJdbcUrl();
    assertTrue(POSTGRES_CONTAINER.isRunning());
  }

  @Test
  @Order(2)
  void getUserAccountTest() throws Exception {
    final ResultActions resultActions = mockMvc.perform(get("/api/user-account"));

    final MvcResult result = resultActions
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andReturn();


    final UserAccountDTO expectedResult = UserAccountDTO.builder()
        .currency(Currency.PLN.name())
        .totalBalance(2454.44)
        .subAccountsIds(List.of(SAVINGS_ACCOUNT_ID_1, SAVINGS_ACCOUNT_TO_DELETE_ID))
        .build();

    assertThat(result.getResponse().getContentAsString(StandardCharsets.UTF_8))
        .isEqualToIgnoringWhitespace(OBJECT_MAPPER.writeValueAsString(expectedResult));
  }

  @Test
  @Order(2)
  void getSavingsAccountTest() throws Exception {
    final ResultActions resultActions = mockMvc.perform(get("/api/account/" + SAVINGS_ACCOUNT_ID_1));

    final MvcResult result = resultActions
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andReturn();


    final AccountDTO expectedResult = SavingsAccountDTO.builder()
        .accountId(SAVINGS_ACCOUNT_ID_1)
        .name("first bank account")
        .description("savings account description")
        .currency(Currency.USD.name())
        .balance(527.78)
        .build();

    assertThat(result.getResponse().getContentAsString(StandardCharsets.UTF_8))
        .isEqualToIgnoringWhitespace(OBJECT_MAPPER.writeValueAsString(expectedResult));
  }

  @Test
  @Order(3)
  void deleteSavingsAccountTest() throws Exception {
    final ResultActions resultActions = mockMvc.perform(delete("/api/account/" + SAVINGS_ACCOUNT_TO_DELETE_ID));

    final MvcResult result = resultActions
        .andExpect(status().isNoContent())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andReturn();


    final String expectedResult = "Account with id: " + SAVINGS_ACCOUNT_TO_DELETE_ID + " was successfully deleted";

    assertThat(result.getResponse().getContentAsString(StandardCharsets.UTF_8))
        .isEqualToIgnoringWhitespace(expectedResult);
  }
}
