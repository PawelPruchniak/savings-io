package pp.pl.io.savings;


import lombok.val;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pp.pl.io.savings.utils.TestFileReader.fromFile;

class AccountIT extends CommonIT {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private SavingsSecurityService savingsSecurityService;

  public static final String SAVINGS_ACCOUNT_ID_1 = "00000001-e89b-42d3-a456-556642440000";
  public static final String SAVINGS_ACCOUNT_TO_DELETE_ID = "00000002-e89b-42d3-a456-556642440000";
  public static final String SAVINGS_ACCOUNT_TO_UPDATE_ID = "00000003-e89b-42d3-a456-556642440000";
  public static final String SAVINGS_ACCOUNT_TO_MIN_UPDATE_ID = "00000004-e89b-42d3-a456-556642440000";

  private static final String CREATE_SAVINGS_ACCOUNT_REQUEST_FILE = "create-savings.account.json";
  private static final String UPDATE_SAVINGS_ACCOUNT_REQUEST_FILE = "update-savings-account.json";
  private static final String MIN_UPDATE_SAVINGS_ACCOUNT_REQUEST_FILE = "min-update-savings-account.json";

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
        .totalBalance(2497.18)
        .subAccountsIds(List.of(
            SAVINGS_ACCOUNT_ID_1, SAVINGS_ACCOUNT_TO_DELETE_ID,
            SAVINGS_ACCOUNT_TO_UPDATE_ID, SAVINGS_ACCOUNT_TO_MIN_UPDATE_ID))
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

  @Test
  @Order(4)
  void createSavingsAccountTest() throws Exception {
    final ResultActions createResultActions = mockMvc.perform(
        post("/api/account")
            .content(fromFile(CREATE_SAVINGS_ACCOUNT_REQUEST_FILE))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
    );
    final MvcResult createResult = createResultActions
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andReturn();

    val createdAccountId = createResult.getResponse().getContentAsString();


    final ResultActions getResultActions = mockMvc.perform(get("/api/account/" + createdAccountId));
    final MvcResult getResult = getResultActions
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andReturn();

    final AccountDTO getExpectedResult = SavingsAccountDTO.builder()
        .accountId(createdAccountId)
        .name("Savings account")
        .description("Savings account description")
        .currency(Currency.PLN.name())
        .balance(250.55)
        .build();

    assertThat(createResult.getResponse().getContentAsString(StandardCharsets.UTF_8))
        .isNotBlank();
    assertThat(getResult.getResponse().getContentAsString(StandardCharsets.UTF_8))
        .isEqualToIgnoringWhitespace(OBJECT_MAPPER.writeValueAsString(getExpectedResult));
  }

  @Test
  @Order(5)
  void updateSavingsAccountTest() throws Exception {
    final ResultActions createResultActions = mockMvc.perform(
        put("/api/account")
            .content(fromFile(UPDATE_SAVINGS_ACCOUNT_REQUEST_FILE))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
    );
    final MvcResult updatedResult = createResultActions
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andReturn();


    final ResultActions getResultActions = mockMvc.perform(get("/api/account/" + SAVINGS_ACCOUNT_TO_UPDATE_ID));
    final MvcResult getResult = getResultActions
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andReturn();

    final AccountDTO getExpectedResult = SavingsAccountDTO.builder()
        .accountId(SAVINGS_ACCOUNT_TO_UPDATE_ID)
        .name("Updated savings account")
        .description("Updated savings account description")
        .currency(Currency.USD.name())
        .balance(10.55)
        .build();

    assertThat(updatedResult.getResponse().getContentAsString(StandardCharsets.UTF_8))
        .isEqualTo("Account with id: " + SAVINGS_ACCOUNT_TO_UPDATE_ID + " was successfully updated");
    assertThat(getResult.getResponse().getContentAsString(StandardCharsets.UTF_8))
        .isEqualToIgnoringWhitespace(OBJECT_MAPPER.writeValueAsString(getExpectedResult));
  }

  @Test
  @Order(5)
  void minimalUpdateSavingsAccountTest() throws Exception {
    final ResultActions createResultActions = mockMvc.perform(
        put("/api/account")
            .content(fromFile(MIN_UPDATE_SAVINGS_ACCOUNT_REQUEST_FILE))
            .contentType(MediaType.APPLICATION_JSON_VALUE)
    );
    final MvcResult updatedResult = createResultActions
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andReturn();


    final ResultActions getResultActions = mockMvc.perform(get("/api/account/" + SAVINGS_ACCOUNT_TO_MIN_UPDATE_ID));
    final MvcResult getResult = getResultActions
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andReturn();

    final AccountDTO getExpectedResult = SavingsAccountDTO.builder()
        .accountId(SAVINGS_ACCOUNT_TO_MIN_UPDATE_ID)
        .currency(Currency.EUR.name())
        .balance(0.0)
        .build();

    assertThat(updatedResult.getResponse().getContentAsString(StandardCharsets.UTF_8))
        .isEqualTo("Account with id: " + SAVINGS_ACCOUNT_TO_MIN_UPDATE_ID + " was successfully updated");
    assertThat(getResult.getResponse().getContentAsString(StandardCharsets.UTF_8))
        .isEqualToIgnoringWhitespace(OBJECT_MAPPER.writeValueAsString(getExpectedResult));
  }
}
