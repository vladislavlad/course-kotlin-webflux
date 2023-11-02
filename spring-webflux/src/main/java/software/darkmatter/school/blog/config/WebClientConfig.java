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
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(WebClientConfig.CodeApiClientProperties.class)
public class WebClientConfig {

    private final CodeApiClientProperties props;

    @Bean
    public WebClient codeWebClient() {
        return WebClient.create(props.getHost());
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
