package pp.pl.io.savings.exchange;

import io.vavr.control.Option;
import io.vavr.control.Try;
import pp.pl.io.savings.account.Currency;

public interface ExchangeRatesAdapter {

  Try<Option<Double>> getExchangeRate(Currency currencyFrom, Currency currencyTo);
}
