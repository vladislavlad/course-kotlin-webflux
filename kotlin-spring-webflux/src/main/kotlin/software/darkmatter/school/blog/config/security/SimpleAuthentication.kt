package software.darkmatter.school.blog.config.security

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.ReactiveSecurityContextHolder

class SimpleAuthentication(
    val userId: Long,
    private val name: String,
    private var authenticated: Boolean = true,
) : Authentication {

    override fun getName() = name

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableListOf<GrantedAuthority>(SimpleGrantedAuthority("USER"))

    override fun getPrincipal() = userId

    override fun getCredentials() = "null"

    override fun getDetails() = null

    override fun isAuthenticated() = authenticated

    override fun setAuthenticated(isAuthenticated: Boolean) {
        this.authenticated = isAuthenticated
    }

    companion object {

        suspend fun simpleAuthFromContext(): SimpleAuthentication {
            return ReactiveSecurityContextHolder
                .getContext()
                .awaitSingle()
                .authentication
                .let {
                    when (it) {
                        null -> throw IllegalStateException("Authentication is null")
                        is SimpleAuthentication -> it
                        else -> throw IllegalStateException("Authentication is not SimpleAuthentication")
                    }
                }
        }
    }
}
