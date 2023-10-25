package software.darkmatter.school.blog.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.invoke
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter

@Configuration
@EnableWebFluxSecurity
class SecurityConfig(
    private val authenticationManager: ReactiveAuthenticationManager,
    private val authenticationConverter: ServerAuthenticationConverter,
) {

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        val authenticationFilter = AuthenticationWebFilter(authenticationManager)
        authenticationFilter.setServerAuthenticationConverter(authenticationConverter)

        return http {
            addFilterAt(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
            authorizeExchange {
                authorize(anyExchange, authenticated)
            }
            httpBasic { disable() }
            formLogin { disable() }
            csrf { disable() }
            cors { disable() }
        }
    }
}
