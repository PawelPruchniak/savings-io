package pp.pl.io.savings.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import pp.pl.io.savings.account.UserAccount;
import pp.pl.io.savings.dto.response.UserAccountDTO;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserAccountMapper {

  public static UserAccountDTO toUserAccountDTO(@NonNull final UserAccount userAccount) {
    return UserAccountDTO.builder()
        .currency(userAccount.getCurrency())
        .totalBalance(DoubleMapper.roundDouble(userAccount.getTotalBalance()))
        .build();
  }
}
