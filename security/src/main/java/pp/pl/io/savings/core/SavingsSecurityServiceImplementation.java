package pp.pl.io.savings.core;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import lombok.AllArgsConstructor;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import pp.pl.io.savings.organisation.SavingsSecurityService;
import pp.pl.io.savings.organisation.UserId;
import pp.pl.io.savings.organisation.UserRepository;
import pp.pl.io.savings.organisation.UserRole;

import java.util.Optional;

@AllArgsConstructor
public class SavingsSecurityServiceImplementation implements SavingsSecurityService {

  private final UserRepository userRepository;

  @Override
  public UserId getUserId() {
    val username = getUsername();
    if (StringUtils.isNotBlank(username)) {

      val userId = userRepository.getUserId(getUsername());
      if (userId.isSuccess() && userId.get().isDefined() && StringUtils.isNotBlank(userId.get().get())) {
        return UserId.of(userId.get().get());
      }
    }
    return null;
  }

  @Override
  public String getUsername() {
    return getUserDetailsFromSecurityContext()
        .map(UserDetails::getUsername)
        .orElse(null);
  }

  @Override
  public Set<UserRole> getUserRoles() {
    return getUserDetailsFromSecurityContext()
        .map(UserDetails::getAuthorities)
        .map(HashSet::ofAll)
        .orElseGet(HashSet::empty)
        .map(grantedAuthority -> UserRole.of(grantedAuthority.getAuthority()));
  }

  private static Optional<UserDetails> getUserDetailsFromSecurityContext() {
    return Optional.ofNullable(getSecurityContext())
        .map(SecurityContext::getAuthentication)
        .map(authentication -> authentication.getPrincipal() instanceof UserDetails userDetails ?
            userDetails : null
        );
  }

  private static SecurityContext getSecurityContext() {
    return SecurityContextHolder.getContext();
  }
}
