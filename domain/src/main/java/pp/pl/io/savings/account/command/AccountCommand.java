package pp.pl.io.savings.account.command;

import pp.pl.io.savings.account.AccountType;

public interface AccountCommand {
  String getName();

  String getDescription();

  AccountType getAccountType();
}
