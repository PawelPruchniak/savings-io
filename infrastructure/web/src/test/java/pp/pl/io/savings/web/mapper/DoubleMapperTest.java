package pp.pl.io.savings.web.mapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pp.pl.io.savings.web.AccountTestData.*;

class DoubleMapperTest {

  @Test
  void shouldThrowWhenValueIsNull() {
    assertThrows(NullPointerException.class, () -> DoubleMapper.roundDouble(null));
  }

  @Test
  void shouldRoundOneDecimalPlaceValue() {
    assertEquals(501.10, DoubleMapper.roundDouble(ONE_DECIMAL_PLACE_VALUE));
  }

  @Test
  void shouldRoundTwoDecimalPlaceValue() {
    assertEquals(2870.82, DoubleMapper.roundDouble(TWO_DECIMAL_PLACE_VALUE));
  }

  @Test
  void shouldRoundUpTwoDecimalPlaceValue() {
    assertEquals(3.99, DoubleMapper.roundDouble(FOUR_DECIMAL_PLACE_VALUE));
  }

}
