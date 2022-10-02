package pp.pl.io.savings.exchange;

import io.vavr.control.Option;
import io.vavr.control.Try;
import pp.pl.io.savings.account.asset.Asset;

public interface CurrencyExchangeRatesAdapter {

  Try<Option<Double>> fetchExchangeRate(Asset currencyFrom, Asset currencyTo);
}
