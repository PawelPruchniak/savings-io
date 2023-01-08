package pp.pl.io.savings.usecases.account.usecases.id;

import lombok.val;
import pp.pl.io.savings.domain.account.AccountId;

import java.util.UUID;

public class UuidService {

  public AccountId createRandomAccountId() {
    val uuid = createRandomUUID();
    return AccountId.of(uuid.toString());
  }

  private UUID createRandomUUID() {
    return UUID.randomUUID();
  }
}
