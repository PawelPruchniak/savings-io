package pp.pl.io.savings.usecases.account.usecases.balance;

import org.junit.jupiter.api.Test;
import pp.pl.io.savings.domain.account.asset.Currency;
import pp.pl.io.savings.domain.account.asset.Stocks;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pp.pl.io.savings.usecases.account.usecases.balance.ExchangeRateTestData.*;

class CurrencyServiceTest {

  private static final BigDecimal SOME_VALUE = BigDecimal.valueOf(250.59);
  private static final BigDecimal SOME_STOCKS_QUANTITY = BigDecimal.valueOf(20);
  private final CurrencyService currencyService = new CurrencyService(
      new TestExchangeRatesStructure(EXCHANGE_PAIR_VALUE_MAP)
  );

  private final CurrencyService currencyServiceWithEmptyStructure = new CurrencyService(
      new TestExchangeRatesStructure()
  );

  @Test
  void shouldRecalculateValueSuccessfullyWithSameCurrency() {
    assertEquals(
        SOME_VALUE,
        currencyService.recalculateValue(SOME_VALUE, Currency.PLN, Currency.PLN)
    );
  }

  @Test
  void shouldRecalculatePlnValueToUsdSuccessfully() {
    assertEquals(
        SOME_VALUE.multiply(BigDecimal.valueOf(PLN_USD_VALUE)),
        currencyService.recalculateValue(SOME_VALUE, Currency.PLN, Currency.USD)
    );
  }

  @Test
  void shouldRecalculatePlnValueToEurSuccessfully() {
    assertEquals(
        SOME_VALUE.multiply(BigDecimal.valueOf(PLN_EUR_VALUE)),
        currencyService.recalculateValue(SOME_VALUE, Currency.PLN, Currency.EUR)
    );
  }

  @Test
  void shouldRecalculateUsdValueToPlnSuccessfully() {
    assertEquals(
        SOME_VALUE.multiply(BigDecimal.valueOf(USD_PLN_VALUE)),
        currencyService.recalculateValue(SOME_VALUE, Currency.USD, Currency.PLN)
    );
  }

  @Test
  void shouldRecalculateUsdValueToEurSuccessfully() {
    assertEquals(
        SOME_VALUE.multiply(BigDecimal.valueOf(USD_EUR_VALUE)),
        currencyService.recalculateValue(SOME_VALUE, Currency.USD, Currency.EUR)
    );
  }

  @Test
  void shouldRecalculateEurValueToPlnSuccessfully() {
    assertEquals(
        SOME_VALUE.multiply(BigDecimal.valueOf(EUR_PLN_VALUE)),
        currencyService.recalculateValue(SOME_VALUE, Currency.EUR, Currency.PLN)
    );
  }

  @Test
  void shouldRecalculateEurValueToUsdSuccessfully() {
    assertEquals(
        SOME_VALUE.multiply(BigDecimal.valueOf(EUR_USD_VALUE)),
        currencyService.recalculateValue(SOME_VALUE, Currency.EUR, Currency.USD)
    );
  }

  @Test
  void shouldReturnSameValueWhenCannotGetExchangeRate() {
    assertEquals(
        SOME_VALUE,
        currencyServiceWithEmptyStructure.recalculateValue(SOME_VALUE, Currency.EUR, Currency.USD)
    );
  }

  @Test
  void shouldRecalculateStocksToEurSuccessfully() {
    assertEquals(
        SOME_STOCKS_QUANTITY.multiply(BigDecimal.valueOf(GPW_EUR_VALUE)),
        currencyService.recalculateValue(SOME_STOCKS_QUANTITY, Stocks.GPW, Currency.EUR)
    );
  }

  @Test
  void shouldRecalculateStocksToPlnSuccessfully() {
    assertEquals(
        SOME_STOCKS_QUANTITY.multiply(BigDecimal.valueOf(GPW_PLN_VALUE)),
        currencyService.recalculateValue(SOME_STOCKS_QUANTITY, Stocks.GPW, Currency.PLN)
    );
  }

  @Test
  void shouldRecalculateStocksToUsdSuccessfully() {
    assertEquals(
        SOME_STOCKS_QUANTITY.multiply(BigDecimal.valueOf(GPW_USD_VALUE)),
        currencyService.recalculateValue(SOME_STOCKS_QUANTITY, Stocks.GPW, Currency.USD)
    );
  }
}
