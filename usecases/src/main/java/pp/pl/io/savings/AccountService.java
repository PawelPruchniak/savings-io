package pp.pl.io.savings;

import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import pp.pl.io.savings.account.Account;
import pp.pl.io.savings.account.AccountRepository;
import pp.pl.io.savings.exception.Error;
import pp.pl.io.savings.organisation.SavingsSecurityService;

@Slf4j
@AllArgsConstructor
public class AccountService {

  private final AccountRepository accountRepository;

  private final SavingsSecurityService savingsSecurityService;

  public Either<Error, Account> getAccount(String accountId) {
    try {
      log.debug("Getting account: {}", accountId);

      if (StringUtils.isBlank(accountId)) {
        return Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR,
            "Account id cannot be blank")
        );
      }

      val userId = savingsSecurityService.getUserId();
      if (StringUtils.isBlank(userId)) {
        return Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR,
            "Cannot compute user")
        );
      }

      val account = accountRepository.fetchAccount(accountId, userId);
      if (account.isFailure()) {
        return Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR,
            "Cannot get account with id: " + accountId)
        );
      }

      if (account.get().isEmpty()) {
        return Either.left(new Error(Error.ErrorCategory.NOT_FOUND,
            "Account with id: " + accountId + " not found")
        );
      }

      return Either.right(account.get().get());
    } catch (final Throwable t) {
      log.warn("Failed getting user account", t);
      return Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, t));
    }
  }
}
