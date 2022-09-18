package pp.pl.io.savings.account.balance;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import pp.pl.io.savings.exchange.ExchangePair;

public class ExchangeRateTestData {

  protected static final Double EUR_PLN_VALUE = 4.72;
  protected static final Double EUR_USD_VALUE = 1.00;
  protected static final Double PLN_EUR_VALUE = 0.21;
  protected static final Double PLN_USD_VALUE = 0.21;
  protected static final Double USD_EUR_VALUE = 1.00;
  protected static final Double USD_PLN_VALUE = 4.71;
  protected static final Map<ExchangePair, Double> EXCHANGE_PAIR_VALUE_MAP = HashMap.of(
      ExchangePair.EUR_PLN, EUR_PLN_VALUE,
      ExchangePair.EUR_USD, EUR_USD_VALUE,
      ExchangePair.PLN_EUR, PLN_EUR_VALUE,
      ExchangePair.PLN_USD, PLN_USD_VALUE,
      ExchangePair.USD_EUR, USD_EUR_VALUE,
      ExchangePair.USD_PLN, USD_PLN_VALUE
  );
}
