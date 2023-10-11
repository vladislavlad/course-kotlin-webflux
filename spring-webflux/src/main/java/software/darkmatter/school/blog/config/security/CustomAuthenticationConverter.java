package software.darkmatter.school.blog.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import software.darkmatter.school.blog.domain.user.business.UserService;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationConverter implements ServerAuthenticationConverter {

    public static final String PREFIX_USER_ID = "User-Id ";
    public static final String AUTHORIZATION = "Authorization";

    private final UserService userService;

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        Optional<Mono<Authentication>> authenticationMono =
            Optional.ofNullable(exchange.getRequest().getHeaders().get(AUTHORIZATION))
                    .flatMap(headers -> headers.stream().findFirst())
                    .filter(header -> header.startsWith(PREFIX_USER_ID))
                    .map(authorizationHeader -> {
                        String userId = authorizationHeader.substring(PREFIX_USER_ID.length());
                        return userService.getById(Long.valueOf(userId))
                                          .map(
                                              user -> new SimpleAuthentication(
                                                  user.getId(),
                                                  user.getFirstName()
                                              )
                                          );
                    });

        return authenticationMono.orElse(Mono.empty());
    }
}
