package software.darkmatter.school.blog.config

import jakarta.validation.constraints.NotNull
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.validation.annotation.Validated
import org.springframework.web.reactive.function.client.WebClient

@Configuration
@EnableConfigurationProperties(WebClientConfig.CodeApiClientProperties::class)
class WebClientConfig(
    private val props: CodeApiClientProperties,
) {

    @Bean
    fun codeWebClient() = WebClient.builder()
        .baseUrl(props.host!!)
        .build()

    @Validated
    @ConfigurationProperties(prefix = "code-api-client")
    class CodeApiClientProperties {

        @field:NotNull
        var host: String? = null
    }
}
