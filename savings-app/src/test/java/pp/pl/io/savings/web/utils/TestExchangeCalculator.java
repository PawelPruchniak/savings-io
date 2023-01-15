package pp.pl.io.savings.web.utils;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import pp.pl.io.savings.domain.exchange.ExchangePair;
import pp.pl.io.savings.domain.exchange.ExchangeRatesStructure;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
public class TestExchangeCalculator {

  public static Double calculateExchange(final ExchangeRatesStructure exchangeRatesStructure, final ExchangePair exchangePair,
                                         final Double value) {
    val exchangeRate = exchangeRatesStructure.getExchangeRatesMap(exchangePair);
    if (exchangeRate.isFailure() || exchangeRate.get().isEmpty()) {
      throw new IllegalArgumentException("Cannot calculate exchange for tests");
    }
    val exchangeValue = roundValue(exchangeRate.get().get() * value);

    log.debug("Exchange calculated for: {} with value: {}", exchangePair.name(), exchangeValue);
    return exchangeValue;
  }

  public static Double roundValue(final Double value) {
    BigDecimal roundedValue = BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    return roundedValue.doubleValue();
  }
}
