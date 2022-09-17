package pp.pl.io.savings.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pp.pl.io.savings.exchange.CachingExchangeRatesStructure;
import pp.pl.io.savings.exchange.ExchangeRatesService;
import pp.pl.io.savings.exchange.ExchangeRatesStructure;
import pp.pl.io.savings.exchange.OpenExchangeRatesService;

@Configuration
public class DomainConfiguration {

  @Bean
  ExchangeRatesService exchangeRatesService() {
    return new OpenExchangeRatesService();
  }

  @Bean(destroyMethod = "tearDown")
  ExchangeRatesStructure exchangeRatesStructure(final ExchangeRatesService exchangeRatesService) {
    return new CachingExchangeRatesStructure(exchangeRatesService);
  }
}
