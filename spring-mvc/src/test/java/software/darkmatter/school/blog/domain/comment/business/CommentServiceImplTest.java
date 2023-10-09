package software.darkmatter.school.blog.domain.comment.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.springframework.security.core.context.SecurityContextHolder;
import software.darkmatter.school.blog.api.dto.CommentUpdateDto;
import software.darkmatter.school.blog.config.security.SimpleAuthentication;
import software.darkmatter.school.blog.domain.comment.data.Comment;
import software.darkmatter.school.blog.domain.comment.data.CommentRepository;
import software.darkmatter.school.blog.domain.comment.error.CommentNotFoundException;
import software.darkmatter.school.blog.domain.post.business.PostService;
import software.darkmatter.school.blog.domain.post.data.Post;
import software.darkmatter.school.blog.domain.user.business.UserService;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static software.darkmatter.school.blog.domain.Constants.TEST_USER;

public class CommentServiceImplTest {

    private final CommentRepository repository = mock();
    private final UserService userService = mock();
    private final PostService postService = mock();

    private final CommentService service = new CommentServiceImpl(repository, userService, postService);

    private static final Long COMMENT_ID = 1L;

    private static final Long POST_ID = 1L;

    private Comment comment;

    @BeforeEach
    public void setUp() {
        OffsetDateTime now = OffsetDateTime.now();

        Post post = new Post();
        post.setId(POST_ID);
        post.setTitle("title");
        post.setSummary("summary");
        post.setContent("content");
        post.setCreatedAt(now);
        post.setUpdatedAt(now);
        post.setPublishedAt(now);

        comment = new Comment();
        comment.setId(COMMENT_ID);
        comment.setPost(post);
        comment.setText("text");
        comment.setCreatedAt(now);
        comment.setUpdatedAt(now);

        SecurityContextHolder.getContext().setAuthentication(new SimpleAuthentication(TEST_USER.getId(), TEST_USER.getFirstName()));
    }

    @Test
    public void getByIdNotFound() {
        when(repository.findByIdAndDeletedAtIsNull(COMMENT_ID)).thenReturn(Optional.of(comment));
        when(repository.findById(2L)).thenThrow(new CommentNotFoundException(2L));

        Comment found = service.getById(COMMENT_ID);

        verify(repository).findByIdAndDeletedAtIsNull(COMMENT_ID);
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(COMMENT_ID);

        assertThrows(CommentNotFoundException.class, () -> service.getById(2L));
        verify(repository).findByIdAndDeletedAtIsNull(2L);
    }

    @Test
    public void update() {
        when(repository.findByIdAndDeletedAtIsNull(COMMENT_ID)).thenReturn(Optional.of(comment));
        when(repository.save(any())).then(AdditionalAnswers.returnsFirstArg());

        Comment updated = service.update(1L, new CommentUpdateDto("upd text"));

        verify(repository).findByIdAndDeletedAtIsNull(COMMENT_ID);
        verify(repository).save(any());

        assertThat(updated.getId()).isEqualTo(POST_ID);
        assertThat(updated.getText()).isEqualTo("upd text");
    }
}
