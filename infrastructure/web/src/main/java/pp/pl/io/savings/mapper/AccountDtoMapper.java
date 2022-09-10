package pp.pl.io.savings.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import pp.pl.io.savings.account.Account;
import pp.pl.io.savings.account.AccountType;
import pp.pl.io.savings.account.SavingsAccount;
import pp.pl.io.savings.dto.response.AccountDTO;
import pp.pl.io.savings.dto.response.SavingsAccountDTO;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountDtoMapper {

  public static AccountDTO toAccountDTO(@NonNull final Account account) {
    if (account.getAccountType() == AccountType.SAVINGS) {
      return mapToSavingsAccountDTO((SavingsAccount) account);
    }
    throw new IllegalArgumentException("This account type: " + account.getAccountType() + " is not supported");
  }

  private static SavingsAccountDTO mapToSavingsAccountDTO(final SavingsAccount savingsAccount) {
    return SavingsAccountDTO.builder()
        .accountId(savingsAccount.getAccountId().code)
        .name(savingsAccount.getName())
        .description(savingsAccount.getDescription())
        .currency(savingsAccount.getCurrency().name())
        .balance(DoubleMapper.roundDouble(savingsAccount.getBalance()))
        .build();
  }
}
