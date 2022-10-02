package pp.pl.io.savings.account.balance;

import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.EnumUtils;
import pp.pl.io.savings.account.asset.Asset;
import pp.pl.io.savings.account.asset.Currency;
import pp.pl.io.savings.exchange.ExchangePair;
import pp.pl.io.savings.exchange.ExchangeRatesStructure;

import java.math.BigDecimal;

@Slf4j
@AllArgsConstructor
public class CurrencyService {

  private final ExchangeRatesStructure exchangeRatesStructure;

  public BigDecimal recalculateValue(final BigDecimal value,
                                     final Asset assetFrom,
                                     final Currency currencyTo) {
    log.debug("Recalculating value from: {} with type: {} to: {}, with base value: {}", assetFrom.getCode(),
        assetFrom.getType(), currencyTo.getCode(), value);

    if (assetFrom.equals(currencyTo)) {
      return value;
    }

    if (!EnumUtils.isValidEnum(ExchangePair.class, assetFrom.getCode() + "_" + currencyTo.getCode())) {
      log.error("Could not recalculate value for {} to {}, because that exchange pair doesn't exists",
          assetFrom.getCode(), currencyTo.getCode());
      return value;
    }

    val exchangeRate = getExchangeRate(assetFrom, currencyTo);
    if (exchangeRate.isFailure() || exchangeRate.get().isEmpty()) {
      log.warn("Could not get exchange rate for {} to {}, from structure", assetFrom.getCode(), currencyTo.getCode());
      return value;
    }

    return value.multiply(BigDecimal.valueOf(exchangeRate.get().get()));
  }

  private Try<Option<Double>> getExchangeRate(final Asset assetFrom, final Currency currencyTo) {
    final ExchangePair exchangePair = ExchangePair.valueOf(composeExchangeRateName(assetFrom, currencyTo));
    return exchangeRatesStructure.getExchangeRatesMap(exchangePair);
  }

  private String composeExchangeRateName(final Asset assetFrom, final Currency currencyTo) {
    return assetFrom.getCode() + "_" + currencyTo.getCode();
  }

}
