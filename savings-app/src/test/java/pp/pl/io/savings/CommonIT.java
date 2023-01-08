package pp.pl.io.savings;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pp.pl.io.savings.domain.organisation.UserRole;
import pp.pl.io.savings.web.utils.TestDbExtension;


@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration-tests")
@ExtendWith(TestDbExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommonIT {

  private static final String POSTGRES_VERSION = "postgres:14.4-alpine";

  @Container
  public static final PostgreSQLContainer POSTGRES_CONTAINER = new PostgreSQLContainer(POSTGRES_VERSION)
      .withDatabaseName("savings-it")
      .withUsername("savings-it-user")
      .withPassword("savings-it-password");

  public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  public static final String TEST_USER_ID = "TEST_USER_1";
  public static final String TEST_USERNAME = "alex.smith";
  public static final Set<UserRole> TEST_USER_ROLES = HashSet.of(UserRole.of("USER_ROLE"));
}
