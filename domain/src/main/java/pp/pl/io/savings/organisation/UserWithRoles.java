package pp.pl.io.savings.organisation;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Builder
@Value
public class UserWithRoles {

  @NonNull
  String username;

  @Builder.Default
  @NonNull
  Set<UserRole> roles = HashSet.empty();
}
