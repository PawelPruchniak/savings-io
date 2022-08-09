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
public class AccountMapper {

  public static AccountDTO toAccountDTO(@NonNull final Account account) {
    if (account.getAccountType() == AccountType.SAVINGS) {
      return mapSavingsAccountToAccountDTO((SavingsAccount) account);
    }
    throw new IllegalArgumentException("This account type: " + account.getAccountType() + " is not supported");
  }

  private static AccountDTO mapSavingsAccountToAccountDTO(final SavingsAccount savingsAccount) {
    return SavingsAccountDTO.builder()
        .accountId(savingsAccount.getAccountId())
        .name(savingsAccount.getName())
        .description(savingsAccount.getDescription())
        .currency(savingsAccount.getCurrency().name())
        .balance(DoubleMapper.roundDouble(savingsAccount.getBalance()))
        .accountType(savingsAccount.getAccountType().name())
        .build();
  }
}
