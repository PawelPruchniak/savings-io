package pp.pl.io.savings.configuration;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import pp.pl.io.savings.domain.organisation.SecurityService;
import pp.pl.io.savings.domain.organisation.UserRepository;
import pp.pl.io.savings.security.core.*;

import javax.crypto.SecretKey;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@Profile("!integration-tests")
public class SecurityConfiguration {

  @Bean
  public UserDetailsService userDetailsService(final DataSource dataSource) {
    return new SavingsJdbcUserDetailsManager(dataSource);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(final AuthenticationConfiguration authenticationConfiguration)
    throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  UserRepository userRepository(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
    return new DbUserRepository(namedParameterJdbcTemplate);
  }

  @Bean
  public SecurityService savingsSecurityService(final UserRepository userRepository) {
    return new SavingsSecurityService(userRepository);
  }

  @Bean
  SecretKey secretKey() {
    return Keys.secretKeyFor(SignatureAlgorithm.HS512);
  }

  @Bean
  public JwtTokenManager jwtTokenManager(final SecretKey secretKey) {
    return new JwtTokenManager(secretKey);
  }

  @Bean
  public JwtRequestFilter jwtRequestFilter(final UserDetailsService userDetailsService, final JwtTokenManager jwtTokenManager) {
    return new JwtRequestFilter(userDetailsService, jwtTokenManager);
  }

  @Bean
  public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
    return new JwtAuthenticationEntryPoint();
  }

  @Bean
  public SecurityFilterChain filterChain(final HttpSecurity http, final JwtRequestFilter jwtRequestFilter) throws Exception {
    final XorCsrfTokenRequestAttributeHandler delegate = new XorCsrfTokenRequestAttributeHandler();
    delegate.setCsrfRequestAttributeName(null);
    final CsrfTokenRequestHandler csrfTokenRequestHandler = delegate::handle;

    http
      .authorizeHttpRequests(authorize -> authorize
        .requestMatchers("/api/security/**").permitAll()
        .anyRequest().authenticated()
      )
      .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint()))
      .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .csrf(csrf -> csrf
        .ignoringRequestMatchers("/api/security/**")
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .csrfTokenRequestHandler(csrfTokenRequestHandler)
      );

    http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return web ->
      web.debug(false).ignoring().requestMatchers("/css/**", "webjars/**");
  }
}
