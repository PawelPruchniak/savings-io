package pp.pl.io.savings.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import pp.pl.io.savings.domain.organisation.SecurityService;

@Configuration
@EnableWebSecurity
@Profile("integration-tests")
public class TestSecurityConfiguration implements WebSecurityCustomizer {

  @Bean
  SecurityService savingsSecurityService() {
    return new TestSavingsApplicationService();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf().disable().authorizeRequests().anyRequest().permitAll();
    return http.build();
  }

  @Override
  public void customize(WebSecurity web) {
    web.debug(false);
  }
}
