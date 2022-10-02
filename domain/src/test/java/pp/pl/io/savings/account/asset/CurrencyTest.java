package pp.pl.io.savings.account.asset;

import lombok.val;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CurrencyTest {

  @Test
  void shouldThrowIllegalArgumentExceptionWhenCurrencyCodeIsInvalid() {
    assertThrows(IllegalArgumentException.class, () -> Currency.of("SOME_INVALID_CODE"));
  }

  @Test
  void shouldCreatePLNCurrencyCorrectly() {
    val currency = Currency.of("PLN");

    assertEquals(Currency.PLN, currency);
    assertEquals(Currency.PLN.getCode(), currency.getCode());
    assertEquals(Currency.PLN.getDescription(), currency.getDescription());
    assertEquals(AssetType.CURRENCY, currency.getType());
  }

  @Test
  void shouldCreateUSDCurrencyCorrectly() {
    val currency = Currency.of("USD");

    assertEquals(Currency.USD, currency);
    assertEquals(Currency.USD.getCode(), currency.getCode());
    assertEquals(Currency.USD.getDescription(), currency.getDescription());
    assertEquals(AssetType.CURRENCY, currency.getType());
  }

  @Test
  void shouldCreateEURCurrencyCorrectly() {
    val currency = Currency.of("EUR");

    assertEquals(Currency.EUR, currency);
    assertEquals(Currency.EUR.getCode(), currency.getCode());
    assertEquals(Currency.EUR.getDescription(), currency.getDescription());
    assertEquals(AssetType.CURRENCY, currency.getType());
  }

}
