package net.azeti.challenge.application.integrationtest.testcontainers.containers.postgres;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;

import java.util.Map;

public class PostgresTestcontainerInitializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext context) {
        final var testcontainersEnabled = context.getEnvironment()
                .getProperty("testcontainers.enabled");
        if (!"true".equals(testcontainersEnabled)) {
            var env = context.getEnvironment();
            env.getPropertySources().addFirst(new MapPropertySource(
                    "testcontainers",
                    // All of this is needed to completely disable
                    // anything, what could trigger errors, when running
                    // unit tests, working with databases
                    //
                    // Those tests should just be ignored if
                    // testcontainers.enabled is false
                    // Use @EnabledIf(expression = "${testcontainers.enabled}
                    //                loadContext = true)
                    // to disable tests working with testcontainers
                    Map.of(
                            // Running postgresql specific liquibase on
                            // h2 just triggers errors before Spring
                            // understands we should not run tests at all
                            "spring.liquibase.enabled", "false",
                            // if hibernate tries to validate ddl it discovers
                            // that there's no tables at all and triggres
                            // errors before Spring understands we should not
                            // run tests at all
                            "spring.jpa.hibernate.ddl-auto", "none",
                            // define testcontainers.enabled if it is not
                            // defined so that
                            // @EnabledIf(expression = "${testcontainers.enabled}
                            //                loadContext = true)
                            // would work
                            "testcontainers.enabled", "false"
                    )
            ));
            return;
        }
        final var postgresContainer = StaticPostgresContainer.getContainer();
        final String jdbcUrl = postgresContainer.getJdbcUrl();
        var env = context.getEnvironment();
        env.getPropertySources().addFirst(new MapPropertySource(
                "testcontainers",
                Map.of(
                        "spring.datasource.url", jdbcUrl,
                        "spring.datasource.username", postgresContainer.getUsername(),
                        "spring.datasource.password", postgresContainer.getPassword()
                )
        ));
    }
}
