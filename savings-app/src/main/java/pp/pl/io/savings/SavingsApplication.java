package pp.pl.io.savings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@SpringBootApplication
public class SavingsApplication {

  public static void main(String[] args) {
    final SpringApplication springApplication = new SpringApplication(SavingsApplication.class);
    springApplication.setDefaultProperties(Map.of("spring.config.on-not-found", "ignore"));
    springApplication.run(args);
  }
}
