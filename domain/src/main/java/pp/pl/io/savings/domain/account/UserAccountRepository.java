package pp.pl.io.savings.domain.account;


import io.vavr.control.Option;
import io.vavr.control.Try;
import pp.pl.io.savings.domain.organisation.UserId;

public interface UserAccountRepository {

  Try<Option<UserAccount>> fetchUserAccount(UserId userId);
}
