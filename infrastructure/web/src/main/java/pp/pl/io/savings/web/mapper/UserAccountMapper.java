package pp.pl.io.savings.web.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import pp.pl.io.savings.domain.account.Account;
import pp.pl.io.savings.domain.account.UserAccount;
import pp.pl.io.savings.web.dto.response.UserAccountDTO;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserAccountMapper {

  public static UserAccountDTO toUserAccountDTO(@NonNull final UserAccount userAccount) {
    return UserAccountDTO.builder()
        .currency(userAccount.getCurrency().getCode())
        .totalBalance(DoubleMapper.roundDouble(userAccount.getTotalBalance()))
        .subAccountsIds(getAccountIds(userAccount))
        .build();
  }

  private static List<String> getAccountIds(final UserAccount userAccount) {
    return userAccount.getAccounts()
            .map(Account::getAccountId)
            .map(accountId -> accountId.code)
            .toJavaList();
  }

}
