package software.darkmatter.school.blog.config.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class SimpleAuthentication implements Authentication {

    private final Long userId;
    private final String name;
    private boolean authenticated = true;

    public static Mono<SimpleAuthentication> simpleAuthFromContext() {
        return ReactiveSecurityContextHolder
            .getContext()
            .map(SecurityContext::getAuthentication)
            .flatMap(authentication -> {
                if (authentication == null) {
                    return Mono.error(new IllegalStateException("Authentication is null"));
                }
                if (SimpleAuthentication.class.isAssignableFrom(authentication.getClass())) {
                    return Mono.just((SimpleAuthentication) authentication);
                }
                return Mono.error(new IllegalStateException("Authentication is not SimpleAuthentication"));
            });
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getPrincipal() {
        return userId;
    }

    @Override
    public Object getCredentials() {
        return "null";
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }
}
