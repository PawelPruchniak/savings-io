package pp.pl.io.savings.usecases.account.usecases.balance;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import io.vavr.control.Try;
import pp.pl.io.savings.domain.exchange.ExchangePair;
import pp.pl.io.savings.domain.exchange.ExchangeRatesStructure;


public class TestExchangeRatesStructure implements ExchangeRatesStructure {

  private final Map<ExchangePair, Double> exchangePairValueMap;

  public TestExchangeRatesStructure() {
    this.exchangePairValueMap = HashMap.empty();
  }

  public TestExchangeRatesStructure(final Map<ExchangePair, Double> exchangePairValueMap) {
    this.exchangePairValueMap = exchangePairValueMap;
  }

  @Override
  public Try<Option<Double>> getExchangeRatesMap(final ExchangePair exchangePair) {
    return Try.of(() -> this.exchangePairValueMap.get(exchangePair));
  }

  @Override
  public void tearDown() {
  }
}
