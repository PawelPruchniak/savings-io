package pp.pl.io.savings;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
  public AccountDTO getUserAccount(@PathVariable String accountId) {
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
}
