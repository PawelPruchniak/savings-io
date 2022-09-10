package pp.pl.io.savings.utils;

import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;

public class TestFileReader {

  private static final String PATH_PREFIX = "db/requests/";

  @SneakyThrows
  public static byte[] fromFile(String path) {
    return new ClassPathResource(PATH_PREFIX + path).getInputStream().readAllBytes();
  }
}
