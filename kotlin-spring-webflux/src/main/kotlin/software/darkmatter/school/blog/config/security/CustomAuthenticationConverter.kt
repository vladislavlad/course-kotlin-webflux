package software.darkmatter.school.blog.config.security

import kotlinx.coroutines.reactor.mono
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import software.darkmatter.school.blog.domain.user.business.UserService

@Component
class CustomAuthenticationConverter(
    private val userService: UserService,
) : ServerAuthenticationConverter {

    override fun convert(exchange: ServerWebExchange): Mono<Authentication> =
        exchange.request.headers[AUTHORIZATION]
            ?.first { it.startsWith(PREFIX_USER_ID) }
            ?.let { authorizationHeader: String ->
                val userId = authorizationHeader.substring(PREFIX_USER_ID.length)

                mono {
                    val user = userService.getById(userId.toLong())

                    SimpleAuthentication(user.id!!, user.firstName)
                }
            } ?: Mono.empty()

    companion object {
        const val PREFIX_USER_ID = "User-Id "
        const val AUTHORIZATION = "Authorization"
    }
}
