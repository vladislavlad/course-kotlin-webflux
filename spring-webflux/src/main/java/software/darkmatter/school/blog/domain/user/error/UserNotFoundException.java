package software.darkmatter.school.blog.domain.user.error;

import lombok.Getter;
import software.darkmatter.school.blog.error.NotFoundException;

@Getter
public class UserNotFoundException extends NotFoundException {

    private final Long userId;

    public UserNotFoundException(Long userId) {
        super("User with id '" + userId + "' was not found.");
        this.userId = userId;
    }
}
