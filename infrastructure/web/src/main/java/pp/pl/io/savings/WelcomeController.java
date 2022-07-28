package pp.pl.io.savings;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/welcome", produces = MediaType.APPLICATION_JSON_VALUE)
public class WelcomeController {

  @GetMapping
  public String getWelcomeMessage() {
    log.debug("Getting welcome message");

    return "Welcome on savings-io app!";
  }
}
