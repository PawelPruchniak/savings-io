package pp.pl.io.savings.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import pp.pl.io.savings.account.AccountType;
import pp.pl.io.savings.account.Currency;
import pp.pl.io.savings.account.command.AccountCommand;
import pp.pl.io.savings.account.command.SavingsAccountCommand;
import pp.pl.io.savings.dto.request.AccountRequest;
import pp.pl.io.savings.dto.request.SavingsAccountRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountCommandMapper {

  private static final Double DEFAULT_BALANCE_VALUE = 0.0D;

  public static AccountCommand toAccountCommand(@NonNull final AccountRequest accountRequest) {
    if (accountRequest.getAccountType().equals(AccountType.SAVINGS.name())) {
      return mapToSavingsAccountCommand((SavingsAccountRequest) accountRequest);
    }
    throw new IllegalArgumentException("This account type: " + accountRequest.getAccountType() + " is not supported");
  }

  private static SavingsAccountCommand mapToSavingsAccountCommand(final SavingsAccountRequest savingsAccountRequest) {
    return SavingsAccountCommand.builder()
        .name(savingsAccountRequest.getName())
        .description(savingsAccountRequest.getDescription())
        .currency(Currency.valueOf(savingsAccountRequest.getCurrency()))
        .balance(savingsAccountRequest.getBalance() != null ? savingsAccountRequest.getBalance() : DEFAULT_BALANCE_VALUE)
        .build();
  }
}
