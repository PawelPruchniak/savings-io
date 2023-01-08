package pp.pl.io.savings.domain.account.create;

import pp.pl.io.savings.domain.account.AccountType;

public interface AccountCommand {
  String getName();

  String getDescription();

  AccountType getAccountType();
}
