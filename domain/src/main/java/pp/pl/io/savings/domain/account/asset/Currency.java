package pp.pl.io.savings.domain.account.asset;


import io.vavr.collection.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Currency implements Asset {

  // Available Currencies
  public static final Currency PLN = new Currency("PLN", "Polish Złoty");
  public static final Currency EUR = new Currency("EUR", "Euro");
  public static final Currency USD = new Currency("USD", "United States dollar");

  private final String code;
  private final String description;

  public AssetType getType() {
    return AssetType.CURRENCY;
  }

  public static Currency of(final String code) {
    if (isInvalid(code)) {
      log.error("Currency with {} code is unavailable", code);
      throw new IllegalArgumentException("Currency with " + code + " is unavailable");
    }
    return AVAILABLE_CURRENCY.find(currency -> currency.code.equals(code)).get();
  }

  private static boolean isInvalid(final String code) {
    return !AVAILABLE_CURRENCY_CODES
        .exists(currencyCode -> currencyCode.equals(code));
  }

  private static final List<String> AVAILABLE_CURRENCY_CODES = List.of(PLN.code, EUR.code, USD.code);
  private static final List<Currency> AVAILABLE_CURRENCY = List.of(PLN, EUR, USD);

}
