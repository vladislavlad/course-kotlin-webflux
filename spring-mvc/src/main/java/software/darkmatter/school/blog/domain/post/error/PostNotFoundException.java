package software.darkmatter.school.blog.domain.post.error;

public class PostNotFoundException extends RuntimeException {

    private Long postId;

    public PostNotFoundException(Long postId) {
        super("Post with id '" + postId + "' was not found ");
    }
}
