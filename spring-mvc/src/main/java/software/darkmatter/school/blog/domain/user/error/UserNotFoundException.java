package software.darkmatter.school.blog.domain.user.error;

public class UserNotFoundException extends RuntimeException {

    private final Long userId;

    public UserNotFoundException(Long userId) {
        super("User with id '" + userId + "' was not found ");
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
