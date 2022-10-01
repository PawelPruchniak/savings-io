package pp.pl.io.savings.account;


public interface Account {

  AccountId getAccountId();

  String getName();

  String getDescription();

  AccountType getAccountType();
}
