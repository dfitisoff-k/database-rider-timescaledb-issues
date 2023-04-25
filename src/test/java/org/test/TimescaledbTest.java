package org.test;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

@Testcontainers
@ActiveProfiles("test")
@DBRider
@ExtendWith(SpringExtension.class)
@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TimescaledbTest {

  private static final DockerImageName IMAGE_NAME = DockerImageName.parse("timescale/timescaledb:2.10.1-pg14")
      .asCompatibleSubstituteFor("postgres");

  @Container
  private static final PostgreSQLContainer<?> TIMESCALE_DB_CONTAINER = new PostgreSQLContainer<>(IMAGE_NAME)
      .withCopyFileToContainer(
          MountableFile.forClasspathResource("migrations"),
          "/docker-entrypoint-initdb.d"
      );

  @DynamicPropertySource
  static void sqlserverProperties(final DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", TIMESCALE_DB_CONTAINER::getJdbcUrl);
    registry.add("spring.datasource.username", TIMESCALE_DB_CONTAINER::getUsername);
    registry.add("spring.datasource.password", TIMESCALE_DB_CONTAINER::getPassword);
  }

  @Test
  @DataSet(value = "dataset/test.yml", cleanAfter = true)
  public void testCount() {
  }

  @Test
  @DataSet(value = "dataset/test.yml", cleanAfter = true)
  public void testB() {

  }
}
