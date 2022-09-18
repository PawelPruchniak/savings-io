package pp.pl.io.savings.exchange;

import pp.pl.io.savings.account.Currency;

public enum ExchangePair {
  PLN_USD(Currency.PLN, Currency.USD),
  PLN_EUR(Currency.PLN, Currency.EUR),
  USD_PLN(Currency.USD, Currency.PLN),
  USD_EUR(Currency.USD, Currency.EUR),
  EUR_PLN(Currency.EUR, Currency.PLN),
  EUR_USD(Currency.EUR, Currency.USD);

  public final Currency currencyFrom;
  public final Currency currencyTo;

  ExchangePair(final Currency currencyFrom, final Currency currencyTo) {
    this.currencyFrom = currencyFrom;
    this.currencyTo = currencyTo;
  }

}
