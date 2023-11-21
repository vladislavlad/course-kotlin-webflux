package software.darkmatter.school.blog.domain;

import software.darkmatter.school.blog.domain.user.data.User;

import java.time.OffsetDateTime;

public class Constants {

    public static final User TEST_USER = new User();

    static {
        TEST_USER.setId(1L);
        TEST_USER.setUsername("username");
        TEST_USER.setFirstName("firstName");
        TEST_USER.setLastName("lastName");
        OffsetDateTime now = OffsetDateTime.now();
        TEST_USER.setCreatedAt(now);
        TEST_USER.setUpdatedAt(now);
    }
}
