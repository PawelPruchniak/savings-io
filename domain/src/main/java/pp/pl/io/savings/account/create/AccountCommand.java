package pp.pl.io.savings.account.create;

import pp.pl.io.savings.account.AccountType;

public interface AccountCommand {
  String getName();

  String getDescription();

  AccountType getAccountType();
}
