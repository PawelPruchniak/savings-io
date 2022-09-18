package pp.pl.io.savings;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import pp.pl.io.savings.account.Currency;
import pp.pl.io.savings.exchange.ExchangeRatesAdapter;

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
 * Exchange rates service, which fetch data from open exchange github project.
 *
 * @see <a href="https://github.com/fawazahmed0/currency-api">Currency-Api</a>
 */
@Slf4j
public class OpenExchangeRatesAdapter implements ExchangeRatesAdapter {

  private static final String REQUEST_PREFIX = "https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/";
  private static final String REQUEST_SUFFIX = ".json";
  public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Override
  public Try<Option<Double>> getExchangeRate(final Currency currencyFrom, final Currency currencyTo) {
    return Try.of(() -> {
      log.debug("Getting exchange rate for {} to {}", currencyFrom.name(), currencyTo.name());

          HttpRequest httpRequest = createHttpRequest(currencyFrom, currencyTo);

          HttpResponse<String> response = HttpClient.newBuilder()
              .build()
              .send(httpRequest, HttpResponse.BodyHandlers.ofString());

          Map<String, String> responseMap = OBJECT_MAPPER.readValue(response.body(), new TypeReference<HashMap<String, String>>() {
          });

          return Option.of(responseMap.get(currencyTo.name().toLowerCase()))
              .map(Double::valueOf)
              .orElse(Option.none());
        }
    );
  }

  private HttpRequest createHttpRequest(final Currency currencyFrom, final Currency currencyTo) throws URISyntaxException {
    return HttpRequest.newBuilder()
        .uri(new URI(REQUEST_PREFIX + currencyFrom.name().toLowerCase() + "/" + currencyTo.name().toLowerCase() + REQUEST_SUFFIX))
        .timeout(Duration.of(10, SECONDS))
        .GET()
        .build();
  }
}
