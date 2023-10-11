package software.darkmatter.school.blog.config.security;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class SimpleAuthenticationManager implements ReactiveAuthenticationManager {

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        if (SimpleAuthentication.class.isAssignableFrom(authentication.getClass())) {
            return Mono.just(authentication);
        }
        return Mono.empty();
    }
}
