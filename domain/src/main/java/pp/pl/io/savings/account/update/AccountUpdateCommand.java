package pp.pl.io.savings.account.update;

import pp.pl.io.savings.account.AccountId;
import pp.pl.io.savings.account.AccountType;


public interface AccountUpdateCommand {
  AccountId getAccountId();

  String getName();

  String getDescription();

  AccountType getAccountType();
}
