package pp.pl.io.savings.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import pp.pl.io.savings.account.Account;
import pp.pl.io.savings.account.UserAccount;
import pp.pl.io.savings.dto.response.UserAccountDTO;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserAccountMapper {

  public static UserAccountDTO toUserAccountDTO(@NonNull final UserAccount userAccount) {
    return UserAccountDTO.builder()
        .currency(userAccount.getCurrency().name())
        .totalBalance(DoubleMapper.roundDouble(userAccount.getTotalBalance()))
        .subAccountsIds(getAccountIds(userAccount))
        .build();
  }

  private static List<String> getAccountIds(@NonNull final UserAccount userAccount) {
    return userAccount.getAccounts()
        .map(Account::getAccountId)
        .map(accountId -> accountId.code)
        .toJavaList();
  }

}
