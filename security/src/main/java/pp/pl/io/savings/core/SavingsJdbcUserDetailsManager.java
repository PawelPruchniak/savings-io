package pp.pl.io.savings.core;

import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

public class SavingsJdbcUserDetailsManager extends JdbcUserDetailsManager {

  private static final String USER_EXISTS_QUERY = "select username from user_profile where username = ?";
  private static final String USERS_BY_USERNAME_QUERY = """
      select username, password, enabled
      from user_profile
      where username = ?
      """;

  public static final String DEF_AUTHORITIES_BY_USERNAME_QUERY = """
      select username, authority
      from user_authorities
      where username = ?
      """;

  public SavingsJdbcUserDetailsManager(DataSource dataSource) {
    super(dataSource);
    setCustomQueries();
  }

  private void setCustomQueries() {
    super.setUserExistsSql(USER_EXISTS_QUERY);
    super.setUsersByUsernameQuery(USERS_BY_USERNAME_QUERY);
    super.setAuthoritiesByUsernameQuery(DEF_AUTHORITIES_BY_USERNAME_QUERY);
  }
}
