package pp.pl.io.savings.account;

import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;

public interface AccountRepository {

  Try<Option<Account>> fetchAccount(String accountId, String userId);

  Try<List<Account>> fetchAccounts(String userId);
}
