package software.darkmatter.school.blog.config;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(RestClientConfig.CodeApiClientProperties.class)
public class RestClientConfig {

    private final CodeApiClientProperties props;

    @Bean
    public RestClient restClient() {
        return RestClient.create(props.host);
    }

    @Getter
    @Setter
    @Validated
    @ConfigurationProperties(prefix = "code-api-client")
    public static class CodeApiClientProperties {

        @NotNull
        private String host;
    }
}
