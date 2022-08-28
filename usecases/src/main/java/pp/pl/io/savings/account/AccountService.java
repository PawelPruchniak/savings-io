package pp.pl.io.savings.account;

import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import pp.pl.io.savings.exception.Error;
import pp.pl.io.savings.organisation.SavingsSecurityService;

@Slf4j
@AllArgsConstructor
public class AccountService {

  private final AccountRepository accountRepository;
  private final SavingsSecurityService savingsSecurityService;

  public Either<Error, Account> getAccount(String accountIdCode) {
    try {
      log.debug("Getting account: {}", accountIdCode);

      if (StringUtils.isBlank(accountIdCode)) {
        return Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR,
            "Account id cannot be blank")
        );
      }
      val accountId = AccountId.of(accountIdCode);

      val userId = savingsSecurityService.getUserId();
      if (userId == null) {
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
      log.warn("Failed getting account", t);
      return Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, t));
    }
  }

  public Either<Error, Void> deleteAccount(String accountIdCode) {
    try {
      log.debug("Deleting account: {}", accountIdCode);

      val getAccountResult = getAccount(accountIdCode);
      if (getAccountResult.isLeft()) {
        return Either.left(getAccountResult.getLeft());
      }

      val account = getAccountResult.get();

      val deleteAccountResult = accountRepository.deleteAccount(account);

      if (deleteAccountResult.isFailure()) {
        return Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR,
            "Cannot delete account with id: " + account.getAccountId())
        );
      }

      return Either.right(null);
    } catch (final Throwable t) {
      log.warn("Failed deleting user account", t);
      return Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, t));
    }
  }
}
