package pp.pl.io.savings.account;

import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;
import pp.pl.io.savings.organisation.UserId;

public interface AccountRepository {

  Try<Option<Account>> fetchAccount(AccountId accountId, UserId userId);

  Try<List<Account>> fetchAccounts(UserId userId);
}
