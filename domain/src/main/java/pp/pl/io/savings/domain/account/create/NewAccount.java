package pp.pl.io.savings.domain.account.create;

import lombok.NonNull;
import pp.pl.io.savings.domain.account.AccountId;
import pp.pl.io.savings.domain.organisation.UserId;

public record NewAccount(@NonNull UserId userId, @NonNull AccountId newAccountId, @NonNull AccountCommand accountCommand) {
}
