package net.azeti.challenge.application.integrationtest.layer.recipe.sharing;

import net.azeti.challenge.application.integrationtest.layer.jpa.TestJpaConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@TestConfiguration
@ComponentScan("net.azeti.challenge.application")
@Import(TestJpaConfiguration.class)
public class TestApplicationConfiguration {
}
