package net.azeti.challenge.application.integrationtest.testcontainers;

import net.azeti.challenge.application.integrationtest.testcontainers.containers.postgres.PostgresTestcontainerInitializer;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class TestcontainersInitializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext context) {
        new PostgresTestcontainerInitializer()
                .initialize(context);
    }
}
