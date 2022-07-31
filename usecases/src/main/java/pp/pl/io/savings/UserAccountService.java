package pp.pl.io.savings;

import io.vavr.control.Either;
import lombok.extern.slf4j.Slf4j;
import pp.pl.io.savings.account.UserAccount;
import pp.pl.io.savings.exception.Error;

@Slf4j
public class UserAccountService {


  public Either<Error, UserAccount> getUserAccount() {
    return null;
  }
}
