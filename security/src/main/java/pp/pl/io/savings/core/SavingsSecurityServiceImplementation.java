package pp.pl.io.savings.core;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import pp.pl.io.savings.organisation.SavingsSecurityService;
import pp.pl.io.savings.organisation.UserRole;

import java.util.Optional;

public class SavingsSecurityServiceImplementation implements SavingsSecurityService {

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
