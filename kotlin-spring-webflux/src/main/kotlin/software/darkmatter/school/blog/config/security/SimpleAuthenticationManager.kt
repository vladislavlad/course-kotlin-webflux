package software.darkmatter.school.blog.config.security

import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class SimpleAuthenticationManager : ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        return when (authentication) {
            is SimpleAuthentication -> {
                Mono.just(authentication)
            }
            else -> Mono.empty()
        }
    }
}
