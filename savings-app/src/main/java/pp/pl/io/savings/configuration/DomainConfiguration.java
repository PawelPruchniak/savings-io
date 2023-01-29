package pp.pl.io.savings.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pp.pl.io.savings.domain.exchange.CachingExchangeRatesStructure;
import pp.pl.io.savings.domain.exchange.CurrencyExchangeRates;
import pp.pl.io.savings.domain.exchange.ExchangeRatesStructure;
import pp.pl.io.savings.web.OpenCurrencyExchangeRates;

@Configuration
public class DomainConfiguration {

  @Bean
  CurrencyExchangeRates currencyExchangeRates() {
    return new OpenCurrencyExchangeRates();
  }

  @Bean(destroyMethod = "tearDown")
  ExchangeRatesStructure exchangeRatesStructure(final CurrencyExchangeRates currencyExchangeRates) {
    return new CachingExchangeRatesStructure(currencyExchangeRates);
  }
}
