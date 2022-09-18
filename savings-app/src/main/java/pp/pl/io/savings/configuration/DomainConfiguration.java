package pp.pl.io.savings.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pp.pl.io.savings.OpenExchangeRatesAdapter;
import pp.pl.io.savings.exchange.CachingExchangeRatesStructure;
import pp.pl.io.savings.exchange.ExchangeRatesAdapter;
import pp.pl.io.savings.exchange.ExchangeRatesStructure;

@Configuration
public class DomainConfiguration {

  @Bean
  ExchangeRatesAdapter exchangeRatesAdapter() {
    return new OpenExchangeRatesAdapter();
  }

  @Bean(destroyMethod = "tearDown")
  ExchangeRatesStructure exchangeRatesStructure(final ExchangeRatesAdapter exchangeRatesAdapter) {
    return new CachingExchangeRatesStructure(exchangeRatesAdapter);
  }
}
