package pp.pl.io.savings.account.balance;

import org.junit.jupiter.api.Test;
import pp.pl.io.savings.account.Currency;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CurrencyServiceTest {

  private final CurrencyService currencyService = new CurrencyService();
  private static final BigDecimal SOME_VALUE = BigDecimal.valueOf(250.59);

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
            SOME_VALUE.multiply(CurrencyService.ExchangeRatePicker.PLN_USD.exchangeRate),
            currencyService.recalculateValue(SOME_VALUE, Currency.PLN, Currency.USD)
    );
  }

  @Test
  void shouldRecalculatePlnValueToEurSuccessfully() {
    assertEquals(
            SOME_VALUE.multiply(CurrencyService.ExchangeRatePicker.PLN_EUR.exchangeRate),
            currencyService.recalculateValue(SOME_VALUE, Currency.PLN, Currency.EUR)
    );
  }

  @Test
  void shouldRecalculateUsdValueToPlnSuccessfully() {
    assertEquals(
            SOME_VALUE.multiply(CurrencyService.ExchangeRatePicker.USD_PLN.exchangeRate),
            currencyService.recalculateValue(SOME_VALUE, Currency.USD, Currency.PLN)
    );
  }

  @Test
  void shouldRecalculateUsdValueToEurSuccessfully() {
    assertEquals(
            SOME_VALUE.multiply(CurrencyService.ExchangeRatePicker.USD_EUR.exchangeRate),
            currencyService.recalculateValue(SOME_VALUE, Currency.USD, Currency.EUR)
    );
  }

  @Test
  void shouldRecalculateEurValueToPlnSuccessfully() {
    assertEquals(
            SOME_VALUE.multiply(CurrencyService.ExchangeRatePicker.EUR_PLN.exchangeRate),
            currencyService.recalculateValue(SOME_VALUE, Currency.EUR, Currency.PLN)
    );
  }

  @Test
  void shouldRecalculateEurValueToUsdSuccessfully() {
    assertEquals(
            SOME_VALUE.multiply(CurrencyService.ExchangeRatePicker.EUR_USD.exchangeRate),
            currencyService.recalculateValue(SOME_VALUE, Currency.EUR, Currency.USD)
    );
  }

}
