package pp.pl.io.savings.account.id;

import lombok.val;
import pp.pl.io.savings.account.AccountId;

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
