package pp.pl.io.savings.organisation;

import io.vavr.collection.Set;

public interface SavingsSecurityService {

  UserId getUserId();

  String getUsername();

  Set<UserRole> getUserRoles();

  default UserWithRoles getUserWithRoles() {
    return UserWithRoles.builder()
        .username(getUsername())
        .roles(getUserRoles())
        .build();
  }
}
