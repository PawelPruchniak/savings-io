package pp.pl.io.savings.account.balance;

import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.EnumUtils;
import pp.pl.io.savings.account.Currency;
import pp.pl.io.savings.exchange.ExchangePair;
import pp.pl.io.savings.exchange.ExchangeRatesStructure;

import java.math.BigDecimal;

@Slf4j
@AllArgsConstructor
public class CurrencyService {

  private final ExchangeRatesStructure exchangeRatesStructure;

  public BigDecimal recalculateValue(final BigDecimal value,
                                     final Currency currencyFrom,
                                     final Currency currencyTo) {
    log.debug("Recalculating value from {} to {}, with base value: {}", currencyFrom.name(), currencyTo.name(), value);

    if (currencyFrom.equals(currencyTo)) {
      return value;
    }

    if (!EnumUtils.isValidEnum(ExchangePair.class, currencyFrom.name() + "_" + currencyTo.name())) {
      log.warn("Could not recalculate value for {} to {}, because that exchange pair doesn't exists",
          currencyFrom.name(), currencyTo.name());
      return value;
    }

    val exchangeRate = getExchangeRate(currencyFrom, currencyTo);
    if (exchangeRate.isFailure() || exchangeRate.get().isEmpty()) {
      log.warn("Could not get exchange rate for {} to {}, from structure", currencyFrom.name(), currencyTo.name());
      return value;
    }

    return value.multiply(BigDecimal.valueOf(exchangeRate.get().get()));
  }

  private Try<Option<Double>> getExchangeRate(Currency currencyFrom, Currency currencyTo) {
    val exchangePair = ExchangePair.valueOf(currencyFrom.name() + "_" + currencyTo.name());
    return exchangeRatesStructure.getExchangeRatesMap(exchangePair);
  }

}
