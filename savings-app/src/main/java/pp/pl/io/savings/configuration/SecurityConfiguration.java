package pp.pl.io.savings.configuration;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import pp.pl.io.savings.core.DbUserRepository;
import pp.pl.io.savings.core.SavingsJdbcUserDetailsManager;
import pp.pl.io.savings.core.SavingsSecurityServiceImplementation;
import pp.pl.io.savings.handler.BasicAuthenticationSuccessHandler;
import pp.pl.io.savings.organisation.SavingsSecurityService;
import pp.pl.io.savings.organisation.UserRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@Profile("!integration-tests")
public class SecurityConfiguration {

  @Bean
  public UserDetailsService userDetailsService(DataSource dataSource) {
    return new SavingsJdbcUserDetailsManager(dataSource);
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  UserRepository userRepository(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    return new DbUserRepository(namedParameterJdbcTemplate);
  }

  @Bean
  public SavingsSecurityService savingsSecurityService(UserRepository userRepository) {
    return new SavingsSecurityServiceImplementation(userRepository);
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .logout()
        .invalidateHttpSession(true)
        .deleteCookies("JSESSIONID");

    http
        .authorizeRequests()
        .mvcMatchers("/login**").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()
        .successHandler(new BasicAuthenticationSuccessHandler());

    return http.build();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return web ->
        web.debug(false).ignoring().mvcMatchers("/css/**", "webjars/**");
  }

  @Bean
  @ConditionalOnProperty(prefix = "pp.pl.io.savings", name = "authentication.test-user", havingValue = "true")
  public InMemoryUserDetailsManager userDetailsService() {
    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    UserDetails user = User.builder()
        .username("some-test-user@gmail.com")
        .password(encoder.encode("test_password"))
        .roles("USER")
        .build();
    return new InMemoryUserDetailsManager(user);
  }
}
