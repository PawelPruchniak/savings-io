package pp.pl.io.savings.account.create;

import lombok.NonNull;
import pp.pl.io.savings.account.AccountId;
import pp.pl.io.savings.organisation.UserId;

public record NewAccount(@NonNull UserId userId, @NonNull AccountId newAccountId, @NonNull AccountCommand accountCommand) {
}
