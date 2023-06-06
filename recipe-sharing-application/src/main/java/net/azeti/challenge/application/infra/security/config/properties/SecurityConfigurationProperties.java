package net.azeti.challenge.application.infra.security.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "security-configuration-properties")
public class SecurityConfigurationProperties {

    private String[] permitAllUrls = new String[]{};
    private String[] swaggerUrls = new String[]{};
}
