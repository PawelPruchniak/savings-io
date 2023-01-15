package pp.pl.io.savings.domain.account;


public interface Account {

  AccountId getAccountId();

  String getName();

  String getDescription();

  AccountType getAccountType();
}
