package pp.pl.io.savings.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import pp.pl.io.savings.account.Account;
import pp.pl.io.savings.account.InvestmentAccount;
import pp.pl.io.savings.account.SavingsAccount;
import pp.pl.io.savings.dto.response.AccountDTO;
import pp.pl.io.savings.dto.response.InvestmentAccountDTO;
import pp.pl.io.savings.dto.response.SavingsAccountDTO;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountDtoMapper {

  public static AccountDTO toAccountDTO(@NonNull final Account account) {
    return switch (account.getAccountType()) {
      case SAVINGS -> mapToSavingsAccountDTO((SavingsAccount) account);
      case INVESTMENT -> mapToInvestmentAccountDTO((InvestmentAccount) account);
    };
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

  private static InvestmentAccountDTO mapToInvestmentAccountDTO(final InvestmentAccount investmentAccount) {
    return InvestmentAccountDTO.builder()
        .accountId(investmentAccount.getAccountId().code)
        .name(investmentAccount.getName())
        .description(investmentAccount.getDescription())
        .asset(investmentAccount.getAsset())
        .assetQuantity(investmentAccount.getAssetQuantity().doubleValue())
        .currencyInvested(investmentAccount.getCurrencyInvested().name())
        .amountInvested(DoubleMapper.roundDouble(investmentAccount.getAmountInvested()))
        .investmentResultValue(DoubleMapper.roundDouble(investmentAccount.getInvestmentResultValue()))
        .investmentResultPercentage(DoubleMapper.roundDouble(investmentAccount.getInvestmentResultPercentage()))
        .build();
  }
}
