package pp.pl.io.savings.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import pp.pl.io.savings.domain.account.asset.Asset;
import pp.pl.io.savings.domain.exchange.CurrencyExchangeRates;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static java.time.temporal.ChronoUnit.SECONDS;

/**
 * Currency exchange rates service, which fetch data from open exchange github project.
 *
 * @see <a href="https://github.com/fawazahmed0/currency-api">Currency-Api</a>
 */
@Slf4j
public class OpenCurrencyExchangeRates implements CurrencyExchangeRates {

  private static final String REQUEST_PREFIX = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/";
  private static final String REQUEST_SUFFIX = ".json";
  public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Override
  public Try<Option<Double>> fetchExchangeRate(final Asset currencyFrom, final Asset assetTo) {
    return Try.of(() -> {
      log.debug("Getting exchange rate for {} to {}", currencyFrom.getCode(), assetTo.getCode());

          HttpRequest httpRequest = createHttpRequest(currencyFrom, assetTo);

          HttpResponse<String> response = HttpClient.newBuilder()
              .build()
              .send(httpRequest, HttpResponse.BodyHandlers.ofString());

          Map<String, String> responseMap = OBJECT_MAPPER.readValue(response.body(), new TypeReference<HashMap<String, String>>() {
          });

          return Option.of(responseMap.get(assetTo.getCode().toLowerCase()))
              .map(Double::valueOf)
              .orElse(Option.none());
        }
    );
  }

  private HttpRequest createHttpRequest(final Asset currencyFrom, final Asset currencyTo) throws URISyntaxException {
    return HttpRequest.newBuilder()
        .uri(new URI(REQUEST_PREFIX + currencyFrom.getCode().toLowerCase() + "/" + currencyTo.getCode().toLowerCase() + REQUEST_SUFFIX))
        .timeout(Duration.of(10, SECONDS))
        .GET()
        .build();
  }
}
