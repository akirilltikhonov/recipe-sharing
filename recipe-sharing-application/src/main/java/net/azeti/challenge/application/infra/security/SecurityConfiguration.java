package net.azeti.challenge.application.infra.security;

import net.azeti.challenge.application.infra.security.model.Role;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // We disabled csrf for simplicity.
        return http.csrf().disable()
                .authorizeRequests().anyRequest().authenticated().and()
                .httpBasic().and()
                .build();
    }

    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        return new InMemoryUserDetailsManager(
                User
                        .withUsername("ro")
                        .password(passwordEncoder().encode("ro"))
                        .authorities(Role.RO.getAuthorities())
                        .build(),
                User
                        .withUsername("rw")
                        .password(passwordEncoder().encode("rw"))
                        .authorities(Role.RW.getAuthorities())
                        .build()
        );
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
