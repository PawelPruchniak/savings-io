package pp.pl.io.savings;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pp.pl.io.savings.account.AccountId;
import pp.pl.io.savings.account.AccountService;
import pp.pl.io.savings.dto.request.create.AccountRequest;
import pp.pl.io.savings.dto.request.update.AccountUpdateRequest;
import pp.pl.io.savings.dto.response.AccountDTO;
import pp.pl.io.savings.mapper.AccountDtoMapper;
import pp.pl.io.savings.mapper.create.AccountCommandMapper;
import pp.pl.io.savings.mapper.update.AccountUpdateCommandMapper;
import pp.pl.io.savings.utils.WebControllerUtils;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/account", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

  private final AccountService accountService;

  @GetMapping(value = "/{accountId}")
  public AccountDTO getAccount(@PathVariable final String accountId) {
    log.debug("Getting account: {}", accountId);

    val result = accountService.getAccount(accountId)
        .map(AccountDtoMapper::toAccountDTO)
        .mapLeft(WebControllerUtils::composeException)
        .peekLeft(e -> log.warn("Failed getting account with id: {}, reason: {}", accountId, e.getMessage()))
        .mapLeft(WebControllerUtils::normalizeError);

    if (result.isLeft()) {
      throw result.getLeft();
    }

    return result.get();
  }

  @DeleteMapping(value = "/{accountId}")
  public ResponseEntity<String> deleteAccount(@PathVariable final String accountId) {
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

  @PostMapping()
  public ResponseEntity<String> createAccount(@RequestBody final AccountRequest accountRequest) {
    log.debug("Creating account with request: {}:", accountRequest);

    val accountCommand = AccountCommandMapper.toAccountCommand(accountRequest);
    val result = accountService.createAccount(accountCommand)
        .mapLeft(WebControllerUtils::composeException)
        .peekLeft(e -> log.warn("Failed creating account with request: {}, reason: {}", accountRequest, e.getMessage()))
        .mapLeft(WebControllerUtils::normalizeError);

    if (result.isLeft()) {
      throw result.getLeft();
    }

    return new ResponseEntity<>(result.get().code, HttpStatus.CREATED);
  }

  @PutMapping()
  public ResponseEntity<String> updateAccount(@RequestBody final AccountUpdateRequest accountUpdateRequest) {
    log.debug("Updating account with request: {}:", accountUpdateRequest);

    if (AccountId.isInvalid(accountUpdateRequest.getAccountId())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account Id is invalid");
    }

    val accountUpdateCommand = AccountUpdateCommandMapper.toAccountUpdateCommand(accountUpdateRequest);
    val result = accountService.updateAccount(accountUpdateCommand)
        .mapLeft(WebControllerUtils::composeException)
        .peekLeft(e -> log.warn("Failed updating account with request: {}, reason: {}", accountUpdateRequest, e.getMessage()))
        .mapLeft(WebControllerUtils::normalizeError);

    if (result.isLeft()) {
      throw result.getLeft();
    }

    return new ResponseEntity<>("Account with id: " + accountUpdateRequest.getAccountId() + " was successfully updated", HttpStatus.OK);
  }
}
