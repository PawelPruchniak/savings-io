package pp.pl.io.savings.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pp.pl.io.savings.exchange.CachingExchangeRatesStructure;
import pp.pl.io.savings.exchange.ExchangeRatesStructure;

@Configuration
public class DomainConfiguration {

  @Bean(destroyMethod = "tearDown")
  ExchangeRatesStructure exchangeRatesStructure() {
    return new CachingExchangeRatesStructure();
  }
}
