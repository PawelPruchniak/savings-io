package pp.pl.io.savings.domain.exchange;

import pp.pl.io.savings.domain.account.asset.Asset;
import pp.pl.io.savings.domain.account.asset.Currency;
import pp.pl.io.savings.domain.account.asset.Stocks;

public enum ExchangePair {

  // Currency Pairs
  PLN_USD(Currency.PLN, Currency.USD, ExchangePairType.CURRENCY),
  PLN_EUR(Currency.PLN, Currency.EUR, ExchangePairType.CURRENCY),
  USD_PLN(Currency.USD, Currency.PLN, ExchangePairType.CURRENCY),
  USD_EUR(Currency.USD, Currency.EUR, ExchangePairType.CURRENCY),
  EUR_PLN(Currency.EUR, Currency.PLN, ExchangePairType.CURRENCY),
  EUR_USD(Currency.EUR, Currency.USD, ExchangePairType.CURRENCY),

  // Stocks Pairs
  GPW_PLN(Stocks.GPW, Currency.PLN, ExchangePairType.STOCKS),
  GPW_USD(Stocks.GPW, Currency.USD, ExchangePairType.STOCKS),
  GPW_EUR(Stocks.GPW, Currency.EUR, ExchangePairType.STOCKS);

  public final Asset assetFrom;
  public final Asset assetTo;
  public final ExchangePairType type;

  ExchangePair(final Asset assetFrom, final Currency assetTo, final ExchangePairType type) {
    this.assetFrom = assetFrom;
    this.assetTo = assetTo;
    this.type = type;
  }
}
