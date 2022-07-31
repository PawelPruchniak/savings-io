package pp.pl.io.savings;

import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import pp.pl.io.savings.account.UserAccount;
import pp.pl.io.savings.account.UserAccountRepository;
import pp.pl.io.savings.exception.Error;

@Slf4j
@AllArgsConstructor
public class UserAccountService {

  private final UserAccountRepository userAccountRepository;

  public Either<Error, UserAccount> getUserAccount() {
    try {
      log.debug("Getting user account");

      val userAccount = userAccountRepository.fetchUserAccount();

      if (userAccount.isFailure()) {
        return Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR,
            "Cannot get user account")
        );
      }

      if (userAccount.get().isEmpty()) {
        return Either.left(new Error(Error.ErrorCategory.NOT_FOUND,
            "User account not found")
        );
      }

      return Either.right(userAccount.get().get());
    } catch (final Throwable t) {
      log.warn("Failed getting user account", t);
      return Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, t));
    }
  }
}
