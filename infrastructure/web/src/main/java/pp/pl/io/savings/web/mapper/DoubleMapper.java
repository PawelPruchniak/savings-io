package pp.pl.io.savings.web.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DoubleMapper {

  private static final int TWO_DECIMAL_PRECISION = 2;

  public static Double roundDouble(@NonNull final BigDecimal value) {
    BigDecimal roundedValue = value.setScale(TWO_DECIMAL_PRECISION, RoundingMode.HALF_UP);
    return roundedValue.doubleValue();
  }
}
