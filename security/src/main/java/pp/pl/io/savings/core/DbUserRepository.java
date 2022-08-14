package pp.pl.io.savings.core;

import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import pp.pl.io.savings.organisation.UserRepository;

@Slf4j
@AllArgsConstructor
public class DbUserRepository implements UserRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private static final String USERNAME_CODE = "username";

  @Override
  public Try<Option<String>> getUserId(final String username) {
    return Try.of(() -> {
          Validate.notBlank(username);
          log.debug("Fetching user id for username: {}", username);

          return Option.of(
                  List.ofAll(
                      jdbcTemplate.query(
                          "select id from user_profile where username =:" + USERNAME_CODE,
                          new MapSqlParameterSource()
                              .addValue(USERNAME_CODE, username),
                          (rs, i) -> rs.getString("id")
                      )
                  )
              )
              .getOrElse(List.empty())
              .headOption();
        }
    );
  }
}
