package pp.pl.io.savings;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pp.pl.io.savings.account.AccountService;
import pp.pl.io.savings.dto.response.AccountDTO;
import pp.pl.io.savings.mapper.AccountMapper;
import pp.pl.io.savings.utils.WebControllerUtils;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/account", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

  private final AccountService accountService;

  @GetMapping(value = "/{accountId}")
  public AccountDTO getAccount(@PathVariable String accountId) {
    log.debug("Getting account: {}", accountId);

    val result = accountService.getAccount(accountId)
        .map(AccountMapper::toAccountDTO)
        .mapLeft(WebControllerUtils::composeException)
        .peekLeft(e -> log.warn("Failed getting account with id: {}, reason: {}", accountId, e.getMessage()))
        .mapLeft(WebControllerUtils::normalizeError);

    if (result.isLeft()) {
      throw result.getLeft();
    }

    return result.get();
  }

  @DeleteMapping(value = "/{accountId}")
  public ResponseEntity<String> deleteAccount(@PathVariable String accountId) {
    log.debug("Deleting account: {}:", accountId);

    val result = accountService.deleteAccount(accountId)
        .mapLeft(WebControllerUtils::composeException)
        .peekLeft(e -> log.warn("Failed deleting account with id: {}, reason: {}", accountId, e.getMessage()))
        .mapLeft(WebControllerUtils::normalizeError);

    if (result.isLeft()) {
      throw result.getLeft();
    }

    return new ResponseEntity<>("Account with id: " + accountId + " was successfully deleted", HttpStatus.NO_CONTENT);
  }
}
