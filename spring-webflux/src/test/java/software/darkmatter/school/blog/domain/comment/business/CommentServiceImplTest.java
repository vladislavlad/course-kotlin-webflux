package software.darkmatter.school.blog.domain.comment.business;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import software.darkmatter.school.blog.api.dto.CommentUpdateDto;
import software.darkmatter.school.blog.config.security.SimpleAuthentication;
import software.darkmatter.school.blog.domain.comment.data.Comment;
import software.darkmatter.school.blog.domain.comment.data.CommentRepository;
import software.darkmatter.school.blog.domain.comment.error.CommentNotFoundException;
import software.darkmatter.school.blog.domain.post.business.PostService;
import software.darkmatter.school.blog.domain.user.business.UserService;

import java.time.OffsetDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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

        comment = new Comment();
        comment.setId(COMMENT_ID);
        comment.setPostId(POST_ID);
        comment.setText("text");
        comment.setCreatedAt(now);
        comment.setUpdatedAt(now);

        SecurityContextHolder.getContext().setAuthentication(new SimpleAuthentication(TEST_USER.getId(), TEST_USER.getFirstName()));
    }

    @Test
    public void getByIdNotFound() {
        when(repository.findByIdAndDeletedAtIsNull(COMMENT_ID)).thenReturn(Mono.just(comment));
        when(repository.findById(2L)).thenThrow(new CommentNotFoundException(2L));

        StepVerifier.create(service.getById(COMMENT_ID))
                    .assertNext(comment -> assertThat(comment.getId()).isEqualTo(COMMENT_ID))
                    .verifyComplete();
        verify(repository).findByIdAndDeletedAtIsNull(COMMENT_ID);

        StepVerifier.create(service.getById(2L))
                    .verifyError(CommentNotFoundException.class);
        verify(repository).findByIdAndDeletedAtIsNull(2L);
    }

    @Test
    public void update() {
        when(repository.findByIdAndDeletedAtIsNull(COMMENT_ID)).thenReturn(Mono.just(comment));
        when(repository.save(any())).then(AdditionalAnswers.returnsFirstArg());

        var updated = service.update(1L, new CommentUpdateDto("upd text"));

        StepVerifier.create(updated)
                    .assertNext(comment -> {
                        assertThat(comment.getId()).isEqualTo(POST_ID);
                        assertThat(comment.getText()).isEqualTo("upd text");
                    })
                    .verifyComplete();

        verify(repository).findByIdAndDeletedAtIsNull(COMMENT_ID);
        verify(repository).save(any());
    }
}
