package pp.pl.io.savings.account;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@EqualsAndHashCode
public class AccountId {

  public final String code;

  public static AccountId of(final String accountId) {
    if (StringUtils.isBlank(accountId)) {
      log.error("AccountId cannot be created from blank string");
      throw new IllegalArgumentException();
    }
    return new AccountId(accountId);
  }

  private AccountId(@NonNull final String accountId) {
    this.code = accountId;
  }

}
