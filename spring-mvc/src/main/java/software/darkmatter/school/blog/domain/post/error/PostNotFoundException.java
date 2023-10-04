package software.darkmatter.school.blog.domain.post.error;

import lombok.Getter;
import software.darkmatter.school.blog.error.NotFoundException;

@Getter
public class PostNotFoundException extends NotFoundException {

    private final Long postId;

    public PostNotFoundException(Long postId) {
        super("Post with id '" + postId + "' was not found ");
        this.postId = postId;
    }
}
