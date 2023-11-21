package software.darkmatter.school.blog.domain;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;
import software.darkmatter.school.blog.config.security.SimpleAuthentication;
import software.darkmatter.school.blog.domain.user.data.User;

import java.time.OffsetDateTime;

public class Constants {

    public static final User TEST_USER = new User();

    public static final long TEST_USER_ID = 1L;

    static {
        TEST_USER.setId(TEST_USER_ID);
        TEST_USER.setUsername("username");
        TEST_USER.setFirstName("firstName");
        TEST_USER.setLastName("lastName");
        OffsetDateTime now = OffsetDateTime.now();
        TEST_USER.setCreatedAt(now);
        TEST_USER.setUpdatedAt(now);
    }

    public static Context TEST_CONTEXT = ReactiveSecurityContextHolder.withSecurityContext(
        Mono.just(
            new SecurityContextImpl(new SimpleAuthentication(TEST_USER.getId(), TEST_USER.getFirstName()))
        )
    );
}
