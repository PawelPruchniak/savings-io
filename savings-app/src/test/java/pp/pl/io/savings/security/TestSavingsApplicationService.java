package pp.pl.io.savings.security;

import io.vavr.collection.Set;
import lombok.Value;
import org.springframework.stereotype.Component;
import pp.pl.io.savings.domain.organisation.SecurityService;
import pp.pl.io.savings.domain.organisation.UserId;
import pp.pl.io.savings.domain.organisation.UserRole;

import static pp.pl.io.savings.CommonIT.*;

@Component
@Value
public class TestSavingsApplicationService implements SecurityService {

  @Override
  public UserId getUserId() {
    return UserId.of(TEST_USER_ID);
  }

  @Override
  public String getUsername() {
    return TEST_USERNAME;
  }

  @Override
  public Set<UserRole> getUserRoles() {
    return TEST_USER_ROLES;
  }
}
