package software.darkmatter.school.blog.domain.comment.error;

import lombok.Getter;
import software.darkmatter.school.blog.error.NotFoundException;

@Getter
public class CommentNotFoundException extends NotFoundException {

    private final Long commentId;

    public CommentNotFoundException(Long commentId) {
        super("Comment with id '" + commentId + "' was not found ");
        this.commentId = commentId;
    }
}
