package pp.pl.io.savings.account;

import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import pp.pl.io.savings.account.create.AccountCommand;
import pp.pl.io.savings.account.create.NewAccount;
import pp.pl.io.savings.account.id.UuidService;
import pp.pl.io.savings.account.update.AccountUpdateCommand;
import pp.pl.io.savings.exception.Error;
import pp.pl.io.savings.organisation.SavingsSecurityService;

@Slf4j
@AllArgsConstructor
public class AccountService {

  private final AccountRepository accountRepository;
  private final SavingsSecurityService savingsSecurityService;
  private final UuidService uuidService;

  public Either<Error, Account> getAccount(final String accountIdCode) {
    try {
      log.debug("Getting account: {}", accountIdCode);

      if (StringUtils.isBlank(accountIdCode)) {
        return Either.left(new Error(Error.ErrorCategory.ILLEGAL_ARGUMENT,
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

  public Either<Error, Void> deleteAccount(final String accountIdCode) {
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
      log.warn("Failed deleting account", t);
      return Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, t));
    }
  }

  public Either<Error, AccountId> createAccount(final AccountCommand accountCommand) {
    try {
      log.debug("Creating account: {}", accountCommand);

      if (accountCommand == null) {
        return Either.left(new Error(Error.ErrorCategory.ILLEGAL_ARGUMENT,
            "Account command cannot be null")
        );
      }

      val userId = savingsSecurityService.getUserId();
      if (userId == null) {
        return Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR,
            "Cannot compute user")
        );
      }

      val newAccountId = uuidService.createRandomAccountId();
      val newAccount = new NewAccount(userId, newAccountId, accountCommand);

      val result = accountRepository.createAccount(newAccount);
      if (result.isFailure()) {
        return Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR,
            "Cannot create account with command: " + accountCommand)
        );
      }

      return Either.right(newAccountId);
    } catch (final Throwable t) {
      log.warn("Failed creating account", t);
      return Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, t));
    }
  }

  public Either<Error, Void> updateAccount(final AccountUpdateCommand accountUpdateCommand) {
    try {
      log.debug("Updating account: {}", accountUpdateCommand);

      //todo: implement this method
      return null;

    } catch (final Throwable t) {
      log.warn("Failed updating account", t);
      return Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, t));
    }
  }
}
