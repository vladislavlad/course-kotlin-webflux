package software.darkmatter.school.blog.domain.post.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class PostAlreadyPublishedException extends ResponseStatusException {

    public PostAlreadyPublishedException() {
        super(HttpStatus.CONFLICT, "Post is already published");
    }
}
