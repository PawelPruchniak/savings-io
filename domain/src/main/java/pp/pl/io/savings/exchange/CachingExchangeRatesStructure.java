package pp.pl.io.savings.exchange;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import pp.pl.io.savings.organisation.AutoReloadingCache;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class CachingExchangeRatesStructure implements AutoReloadingCache, ExchangeRatesStructure {

  private static final long RELOAD_INTERVAL_MILLISECONDS = 3_600_000;

  private final ExchangeRatesService exchangeRatesService;
  private final AtomicReference<ExchangeRatesWithTimestamp> cachedExchangeRatesWithTimestamp;
  private final CountDownLatch exchangeRatesLoadedLatch = new CountDownLatch(1);
  private final Timer timer;

  public CachingExchangeRatesStructure(final ExchangeRatesService exchangeRatesService) {
    this.exchangeRatesService = exchangeRatesService;
    this.cachedExchangeRatesWithTimestamp = new AtomicReference<>(
        new ExchangeRatesWithTimestamp(
            LocalDateTime.MIN,
            HashMap.empty()
        )
    );
    timer = scheduleAutoReloads(this, RELOAD_INTERVAL_MILLISECONDS, log, "exchange rates");
  }

  @Override
  public Try<Option<Double>> getExchangeRatesMap(final ExchangePair exchangePair) {
    return Try.of(() -> {
      exchangeRatesLoadedLatch.await();
      return cachedExchangeRatesWithTimestamp.get()
          .exchangeRatesMap
          .get(exchangePair);
    });
  }

  @Override
  public void checkForUpdates() {
    log.debug("Checking for exchange rates updates");

    try {
      final LocalDateTime lastChange = LocalDateTime.now();

      if (lastChange.equals(cachedExchangeRatesWithTimestamp.get().timestamp())) {
        log.debug("Not excepting exchange rates changes, skipping checking updates");
        return;
      }

      java.util.Map<ExchangePair, Double> newExchangeRatesMap = new java.util.HashMap<>();
      for (ExchangePair exchangePair : ExchangePair.values()) {
        var exchangeRate = exchangeRatesService.getExchangeRate(exchangePair.currencyFrom, exchangePair.currencyTo);
        if (exchangeRate.isFailure() || exchangeRate.get().isEmpty()) {
          log.warn("Could not get exchange rate for pair: {} to {}", exchangePair.currencyFrom.name(), exchangePair.currencyTo.name());
        } else {
          newExchangeRatesMap.put(exchangePair, exchangeRate.get().get());
        }
      }

      cachedExchangeRatesWithTimestamp.set(
          new ExchangeRatesWithTimestamp(lastChange, HashMap.ofAll(newExchangeRatesMap))
      );

      exchangeRatesLoadedLatch.countDown();
      log.info("Cached new exchange rates, dated: {}", lastChange);

    } catch (final Exception e) {
      log.warn("Failed checking for exchange rates updates", e);
    }
  }

  @Override
  public void tearDown() {
    log.warn("Cancelling exchange rates refreshing");
    timer.cancel();
  }

  private record ExchangeRatesWithTimestamp(@NonNull LocalDateTime timestamp, @NonNull Map<ExchangePair, Double> exchangeRatesMap) {
  }
}
