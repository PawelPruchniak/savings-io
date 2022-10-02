package pp.pl.io.savings.account.asset;

import lombok.val;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StocksTest {

  @Test
  void shouldThrowIllegalArgumentExceptionWhenStocksCodeIsInvalid() {
    assertThrows(IllegalArgumentException.class, () -> Stocks.of("SOME_INVALID_CODE"));
  }

  @Test
  void shouldCreateGPWStocksCorrectly() {
    val currency = Stocks.of("GPW");

    assertEquals(Stocks.GPW, currency);
    assertEquals(Stocks.GPW.getCode(), currency.getCode());
    assertEquals(Stocks.GPW.getDescription(), currency.getDescription());
    assertEquals(AssetType.STOCKS, currency.getType());
  }

  @Test
  void shouldCreateXTBStocksCorrectly() {
    val currency = Stocks.of("XTB");

    assertEquals(Stocks.XTB, currency);
    assertEquals(Stocks.XTB.getCode(), currency.getCode());
    assertEquals(Stocks.XTB.getDescription(), currency.getDescription());
    assertEquals(AssetType.STOCKS, currency.getType());
  }

}
