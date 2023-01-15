package pp.pl.io.savings.web.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import static pp.pl.io.savings.CommonIT.POSTGRES_CONTAINER;

@Slf4j
public class TestDbPropertiesPropagator {
  private static final String SPRING_DATASOURCE_URL_PROPERTY_KEY = "spring.datasource.url";
  private static final String SPRING_DATASOURCE_USERNAME_PROPERTY_KEY = "spring.datasource.username";
  private static final String SPRING_DATASOURCE_PASSWORD_PROPERTY_KEY = "spring.datasource.password";

  @SneakyThrows
  public static void propagateDbProperties() {
    log.info("Overwriting DB related Spring properties started.");

    System.setProperty(SPRING_DATASOURCE_URL_PROPERTY_KEY, POSTGRES_CONTAINER.getJdbcUrl());
    System.setProperty(SPRING_DATASOURCE_USERNAME_PROPERTY_KEY, POSTGRES_CONTAINER.getUsername());
    System.setProperty(SPRING_DATASOURCE_PASSWORD_PROPERTY_KEY, POSTGRES_CONTAINER.getPassword());

    log.info("Overwriting DB related Spring properties finished.");
  }

}
