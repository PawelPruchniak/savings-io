package pp.pl.io.savings.domain.organisation;

import org.apache.commons.lang3.Validate;

public record UserRole(String code) {

  public static UserRole of(final String code) {
    return new UserRole(code);
  }

  public UserRole(final String code) {
    Validate.notBlank(code, "User role cannot be blank");
    this.code = code.trim();
  }

  @Override
  public String toString() {
    return "UserRole(" + code + ")";
  }
}
