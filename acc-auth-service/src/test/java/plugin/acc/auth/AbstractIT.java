package plugin.acc.auth;

import lombok.extern.slf4j.*;
import org.springframework.boot.test.context.*;
import org.springframework.context.annotation.*;
import org.springframework.test.context.*;

import org.junit.jupiter.api.*;

@Slf4j
@SpringBootTest
@Profile("test")
public class AbstractIT {

    private static PostgresInstanceRunner postgresRunner = PostgresInstanceRunner.of("postgres:14.17");

    @DynamicPropertySource
    public static void configureProperties(DynamicPropertyRegistry registry) {
        log.info("Configure properties");
        postgresRunner.configureProperties(registry);
    }

    @BeforeAll
    public static void beforeAll() {
        log.info("Start containers");
        postgresRunner.start();
    }

    @AfterAll
    public static void afterAll() {
        log.info("Stop containers");
        postgresRunner.stop();
    }

}
