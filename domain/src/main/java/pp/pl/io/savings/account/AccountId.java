package pp.pl.io.savings.account;

import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@EqualsAndHashCode
public class AccountId {

  public final String code;

  public static AccountId of(final String accountId) {
    if (accountId == null || accountId.length() != 36) {
      log.error("AccountId cannot be created from wrong format string");
      throw new IllegalArgumentException();
    }
    return new AccountId(accountId);
  }

  private AccountId(final String accountId) {
    this.code = UUID.fromString(accountId).toString();
  }

}
