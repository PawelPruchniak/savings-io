package pp.pl.io.savings.domain.organisation;

import io.vavr.control.Option;
import io.vavr.control.Try;

public interface UserRepository {

  Try<Option<String>> getUserId(String username);
}
