package pp.pl.io.savings.account.asset;

import io.vavr.collection.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Stocks implements Asset {

  // Available Stocks
  public static final Stocks GPW = new Stocks("GPW", "Warsaw Stock Exchange");
  public static final Stocks XTB = new Stocks("XTB", "XTB S.A. broker");

  private final String code;
  private final String description;

  @Override
  public AssetType getType() {
    return AssetType.STOCKS;
  }

  public static Stocks of(final String code) {
    if (isInvalid(code)) {
      log.error("Stocks with {} code is unavailable", code);
      throw new IllegalArgumentException("Stocks with " + code + " is unavailable");
    }
    return AVAILABLE_STOCKS.find(stocks -> stocks.code.equals(code)).get();
  }

  private static boolean isInvalid(final String code) {
    return !AVAILABLE_STOCKS_CODES
        .exists(stocksCode -> stocksCode.equals(code));
  }

  private static final List<String> AVAILABLE_STOCKS_CODES = List.of(GPW.code, XTB.code);
  private static final List<Stocks> AVAILABLE_STOCKS = List.of(GPW, XTB);
}
