package pp.pl.io.savings.web.utils;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class TestDbExtension implements BeforeAllCallback {
  @Override
  public void beforeAll(ExtensionContext context) {
    TestDbPropertiesPropagator.propagateDbProperties();
  }

}
