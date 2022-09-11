package pp.pl.io.savings.organisation;

import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@EqualsAndHashCode
public class UserId {

  public final String code;

  public static UserId of(final String userId) {
    if (StringUtils.isBlank(userId)) {
      log.error("UserId cannot be created from blank string");
      throw new IllegalArgumentException();
    }
    return new UserId(userId);
  }

  private UserId(final String userId) {
    this.code = userId;
  }

}
