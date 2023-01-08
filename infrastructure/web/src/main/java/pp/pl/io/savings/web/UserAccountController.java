package pp.pl.io.savings.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pp.pl.io.savings.usecases.account.usecases.UserAccountService;
import pp.pl.io.savings.web.dto.response.UserAccountDTO;
import pp.pl.io.savings.web.mapper.UserAccountMapper;
import pp.pl.io.savings.web.utils.WebControllerUtils;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/user-account", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserAccountController {

  private final UserAccountService userAccountService;

  @GetMapping
  public UserAccountDTO getUserAccount() {
    log.debug("Getting user account");

    val result = userAccountService.getUserAccount()
        .map(UserAccountMapper::toUserAccountDTO)
        .mapLeft(WebControllerUtils::composeException)
        .peekLeft(e -> log.warn("Failed getting user account because: {}", e.getMessage()))
        .mapLeft(WebControllerUtils::normalizeError);

    if (result.isLeft()) {
      throw result.getLeft();
    }

    return result.get();
  }
}
