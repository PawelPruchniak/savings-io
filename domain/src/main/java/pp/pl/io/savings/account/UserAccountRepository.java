package pp.pl.io.savings.account;


import io.vavr.control.Option;
import io.vavr.control.Try;

public interface UserAccountRepository {

  Try<Option<UserAccount>> fetchUserAccount();
}
