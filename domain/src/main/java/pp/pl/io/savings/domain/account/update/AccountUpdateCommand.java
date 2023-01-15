package pp.pl.io.savings.domain.account.update;

import pp.pl.io.savings.domain.account.AccountId;
import pp.pl.io.savings.domain.account.AccountType;


public interface AccountUpdateCommand {
  AccountId getAccountId();

  String getName();

  String getDescription();

  AccountType getAccountType();
}
