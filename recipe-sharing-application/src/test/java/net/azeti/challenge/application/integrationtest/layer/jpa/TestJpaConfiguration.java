package net.azeti.challenge.application.integrationtest.layer.jpa;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan({
        "net.azeti.challenge.application.infra.jpa",
})
public class TestJpaConfiguration {
}
