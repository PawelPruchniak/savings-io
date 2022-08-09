package pp.pl.io.savings.account;

import io.vavr.control.Option;
import io.vavr.control.Try;

public interface AccountRepository {

  Try<Option<Account>> fetchAccount(String accountId, String username);
}
