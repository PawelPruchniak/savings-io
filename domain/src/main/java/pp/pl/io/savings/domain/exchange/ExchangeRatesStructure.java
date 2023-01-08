package pp.pl.io.savings.domain.exchange;

import io.vavr.control.Option;
import io.vavr.control.Try;

public interface ExchangeRatesStructure {

  Try<Option<Double>> getExchangeRatesMap(ExchangePair exchangePair);

  void tearDown();
}
