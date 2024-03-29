package pp.pl.io.savings.web.mapper.update;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import pp.pl.io.savings.domain.account.AccountId;
import pp.pl.io.savings.domain.account.AccountType;
import pp.pl.io.savings.domain.account.asset.Currency;
import pp.pl.io.savings.domain.account.update.AccountUpdateCommand;
import pp.pl.io.savings.domain.account.update.SavingsAccountUpdateCommand;
import pp.pl.io.savings.web.dto.request.update.AccountUpdateRequest;
import pp.pl.io.savings.web.dto.request.update.SavingsAccountUpdateRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountUpdateCommandMapper {

  private static final Double DEFAULT_BALANCE_VALUE = 0.0D;

  public static AccountUpdateCommand toAccountUpdateCommand(@NonNull final AccountUpdateRequest accountUpdateRequest) {
    if (accountUpdateRequest.getAccountType().equals(AccountType.SAVINGS.name())) {
      return mapToSavingsAccountUpdateCommand((SavingsAccountUpdateRequest) accountUpdateRequest);
    }
    throw new IllegalArgumentException("This account type: " + accountUpdateRequest.getAccountType() + " is not supported");
  }

  private static SavingsAccountUpdateCommand mapToSavingsAccountUpdateCommand(final SavingsAccountUpdateRequest savingsAccountRequest) {
    return SavingsAccountUpdateCommand.builder()
        .accountId(AccountId.of(savingsAccountRequest.getAccountId()))
        .name(savingsAccountRequest.getName())
        .description(savingsAccountRequest.getDescription())
        .currency(Currency.of(savingsAccountRequest.getCurrency()))
        .balance(savingsAccountRequest.getBalance() != null ? savingsAccountRequest.getBalance() : DEFAULT_BALANCE_VALUE)
        .build();
  }

}
