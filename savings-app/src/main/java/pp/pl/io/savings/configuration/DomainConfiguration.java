package pp.pl.io.savings.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pp.pl.io.savings.OpenCurrencyExchangeRatesAdapter;
import pp.pl.io.savings.exchange.CachingExchangeRatesStructure;
import pp.pl.io.savings.exchange.CurrencyExchangeRatesAdapter;
import pp.pl.io.savings.exchange.ExchangeRatesStructure;

@Configuration
public class DomainConfiguration {

  @Bean
  CurrencyExchangeRatesAdapter currencyExchangeRatesAdapter() {
    return new OpenCurrencyExchangeRatesAdapter();
  }

  @Bean(destroyMethod = "tearDown")
  ExchangeRatesStructure exchangeRatesStructure(final CurrencyExchangeRatesAdapter currencyExchangeRatesAdapter) {
    return new CachingExchangeRatesStructure(currencyExchangeRatesAdapter);
  }
}
