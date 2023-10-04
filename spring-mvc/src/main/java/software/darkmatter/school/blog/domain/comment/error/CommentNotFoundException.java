package software.darkmatter.school.blog.domain.comment.error;

public class CommentNotFoundException extends RuntimeException {

    private Long commentId;

    public CommentNotFoundException(Long commentId) {
        super("Comment with id '" + commentId + "' was not found ");
    }
}
