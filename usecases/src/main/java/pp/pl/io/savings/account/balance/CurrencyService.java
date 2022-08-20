package pp.pl.io.savings.account.balance;

import pp.pl.io.savings.account.Currency;

import java.math.BigDecimal;

public class CurrencyService {
  public BigDecimal recalculateValue(final BigDecimal value,
                                     final Currency currencyFrom,
                                     final Currency currencyTo) {
    if (currencyFrom.equals(currencyTo)) {
      return value;
    }
    return value.multiply(getExchangeRate(currencyFrom, currencyTo));
  }

  private BigDecimal getExchangeRate(Currency currencyFrom, Currency currencyTo) {
    String currencyExchange = currencyFrom.name() + "_" + currencyTo.name();
    return ExchangeRatePicker.valueOf(currencyExchange).exchangeRate;
  }

  //todo: create service for online exchange rate checking
  public enum ExchangeRatePicker {
    PLN_USD(BigDecimal.valueOf(0.22)),
    PLN_EUR(BigDecimal.valueOf(0.21)),
    USD_PLN(BigDecimal.valueOf(4.61)),
    USD_EUR(BigDecimal.valueOf(0.98)),
    EUR_PLN(BigDecimal.valueOf(4.68)),
    EUR_USD(BigDecimal.valueOf(1.02));

    public final BigDecimal exchangeRate;

    ExchangeRatePicker(BigDecimal exchangeRate) {
      this.exchangeRate = exchangeRate;
    }
  }
}
