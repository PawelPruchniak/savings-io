package pp.pl.io.savings.usecases.account.usecases.id;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class UuidServiceTest {

  private final UuidService uuidService = new UuidService();

  @Test
  void shouldCreateRandomAccountId() {
    assertNotNull(uuidService.createRandomAccountId());
  }

}
