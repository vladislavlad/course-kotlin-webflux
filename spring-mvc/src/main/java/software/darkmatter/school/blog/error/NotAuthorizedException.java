package software.darkmatter.school.blog.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotAuthorizedException extends ResponseStatusException {

    public NotAuthorizedException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
