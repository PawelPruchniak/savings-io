package pp.pl.io.savings.account;

import io.vavr.collection.List;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import pp.pl.io.savings.account.balance.BalanceService;
import pp.pl.io.savings.exception.Error;
import pp.pl.io.savings.organisation.SavingsSecurityService;

@Slf4j
@AllArgsConstructor
public class UserAccountService {

  private final UserAccountRepository userAccountRepository;
  private final AccountRepository accountRepository;
  private final BalanceService balanceService;
  private final SavingsSecurityService savingsSecurityService;

  public Either<Error, UserAccount> getUserAccount() {
    try {
      log.debug("Getting user account");

      val userId = savingsSecurityService.getUserId();
      if (userId == null) {
        return Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR,
            "Cannot compute user")
        );
      }

      var optionUserAccount = userAccountRepository.fetchUserAccount(userId);
      if (optionUserAccount.isFailure()) {
        return Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR,
            "Cannot fetch user account")
        );
      }

      if (optionUserAccount.get().isEmpty()) {
        return Either.left(new Error(Error.ErrorCategory.NOT_FOUND,
            "User account not found")
        );
      }

      val accounts = accountRepository.fetchAccounts(userId);
      if (accounts.isFailure()) {
        return Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR,
            "Cannot fetch related accounts")
        );
      }

      val userAccount = mapToUserAccount(optionUserAccount.get().get(), accounts.get());

      return Either.right(userAccount);
    } catch (final Throwable t) {
      log.warn("Failed getting user account", t);
      return Either.left(new Error(Error.ErrorCategory.PROCESSING_ERROR, t));
    }
  }

  private UserAccount mapToUserAccount(final UserAccount userAccount, final List<Account> accounts) {
    return userAccount.toBuilder()
        .accounts(accounts)
        .currency(userAccount.getCurrency())
        .totalBalance(balanceService.calculateTotalBalance(accounts, userAccount.getCurrency()))
        .build();
  }
}
