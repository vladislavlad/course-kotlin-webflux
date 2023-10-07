package software.darkmatter.school.blog.config;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.flywaydb.core.Flyway;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class FlywayConfig {

    private final FlywayProperties flywayProperties;

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        return Flyway.configure()
                     .dataSource(flywayProperties.url, flywayProperties.user, flywayProperties.password)
                     .locations(flywayProperties.locations.toArray(new String[1]))
                     .baselineOnMigrate(flywayProperties.baselineOnMigrate)
                     .load();
    }

    @Validated
    @Component
    @ConfigurationProperties(prefix = "spring.flyway")
    @Getter
    @Setter
    public static class FlywayProperties {

        @NotEmpty
        private List<String> locations;

        @NotEmpty
        private String url;

        @NotEmpty
        private String user;

        @NotEmpty
        private String password;

        private boolean baselineOnMigrate;
    }
}
