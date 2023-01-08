package pp.pl.io.savings.usecases.account.usecases.balance;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import pp.pl.io.savings.domain.exchange.ExchangePair;

public class ExchangeRateTestData {

  protected static final Double EUR_PLN_VALUE = 4.72;
  protected static final Double EUR_USD_VALUE = 1.00;
  protected static final Double PLN_EUR_VALUE = 0.21;
  protected static final Double PLN_USD_VALUE = 0.21;
  protected static final Double USD_EUR_VALUE = 1.00;
  protected static final Double USD_PLN_VALUE = 4.71;
  protected static final Double GPW_PLN_VALUE = 32.52;
  protected static final Double GPW_USD_VALUE = 6.76;
  protected static final Double GPW_EUR_VALUE = 6.78;
  protected static final Map<ExchangePair, Double> EXCHANGE_PAIR_VALUE_MAP = HashMap.of(
      ExchangePair.EUR_PLN, EUR_PLN_VALUE, ExchangePair.EUR_USD, EUR_USD_VALUE,
      ExchangePair.PLN_EUR, PLN_EUR_VALUE, ExchangePair.PLN_USD, PLN_USD_VALUE,
      ExchangePair.USD_EUR, USD_EUR_VALUE, ExchangePair.USD_PLN, USD_PLN_VALUE,
      ExchangePair.GPW_PLN, GPW_PLN_VALUE, ExchangePair.GPW_USD, GPW_USD_VALUE,
      ExchangePair.GPW_EUR, GPW_EUR_VALUE
  );
}
