package software.darkmatter.school.blog.domain.post.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.springframework.security.core.context.SecurityContextHolder;
import software.darkmatter.school.blog.api.dto.PostCreateDto;
import software.darkmatter.school.blog.api.dto.PostPublishDto;
import software.darkmatter.school.blog.config.security.SimpleAuthentication;
import software.darkmatter.school.blog.domain.code.rest.CodeApiClient;
import software.darkmatter.school.blog.domain.code.rest.dto.ApiResult;
import software.darkmatter.school.blog.domain.code.rest.dto.CodeCheckRequest;
import software.darkmatter.school.blog.domain.post.data.Post;
import software.darkmatter.school.blog.domain.post.data.PostRepository;
import software.darkmatter.school.blog.domain.post.error.PostNotFoundException;
import software.darkmatter.school.blog.domain.user.business.UserService;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static software.darkmatter.school.blog.domain.Constants.TEST_USER;

public class PostServiceImplTest {

    private final PostRepository repository = mock();

    private final UserService userService = mock();

    private final CodeApiClient codeApiClient = mock();

    private final PostService service = new PostServiceImpl(repository, userService, codeApiClient);

    private static final Long USER_ID = 1L;

    private static final Long POST_ID = 1L;

    private Post post;

    @BeforeEach
    public void setUp() {
        OffsetDateTime now = OffsetDateTime.now();

        post = new Post();
        post.setId(POST_ID);
        post.setTitle("title");
        post.setSummary("summary");
        post.setContent("content");
        post.setCreatedAt(now);
        post.setCreatedBy(TEST_USER);
        post.setUpdatedAt(now);
        post.setUpdatedBy(TEST_USER);
        post.setPublishedAt(null);

        SecurityContextHolder.getContext().setAuthentication(new SimpleAuthentication(TEST_USER.getId(), TEST_USER.getFirstName()));
    }

    @Test
    public void getByIdNotFound() {
        when(repository.findByIdAndDeletedAtIsNull(POST_ID)).thenReturn(Optional.of(post));
        when(repository.findByIdAndDeletedAtIsNull(2L)).thenThrow(new PostNotFoundException(2L));

        Post found = service.getById(POST_ID);

        verify(repository).findByIdAndDeletedAtIsNull(POST_ID);
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(POST_ID);

        assertThrows(PostNotFoundException.class, () -> service.getById(2L));
        verify(repository).findByIdAndDeletedAtIsNull(2L);
    }

    @Test
    public void update() {
        when(repository.findByIdAndDeletedAtIsNull(POST_ID)).thenReturn(Optional.of(post));
        when(repository.save(any())).then(AdditionalAnswers.returnsFirstArg());

        OffsetDateTime updatedAt = post.getUpdatedAt();

        Post updated = service.update(1L, new PostCreateDto("upd title", "upd summary", "upd content"));

        verify(repository).findByIdAndDeletedAtIsNull(POST_ID);
        verify(repository).save(any());

        assertThat(updated.getId()).isEqualTo(POST_ID);
        assertThat(updated.getTitle()).isEqualTo("upd title");
        assertThat(updated.getSummary()).isEqualTo("upd summary");
        assertThat(updated.getContent()).isEqualTo("upd content");
        assertThat(updated.getUpdatedAt()).isNotEqualTo(updatedAt);
    }

    @Test
    public void delete() {
        when(repository.findByIdAndDeletedAtIsNull(POST_ID)).thenReturn(Optional.of(post));
        when(repository.save(any())).then(AdditionalAnswers.returnsFirstArg());

        OffsetDateTime updatedAt = post.getUpdatedAt();

        service.delete(1L);

        verify(repository).findByIdAndDeletedAtIsNull(POST_ID);
        verify(repository).save(any());

        assertThat(post.getDeletedAt()).isNotNull();

        assertThat(post.getId()).isEqualTo(POST_ID);
        assertThat(post.getTitle()).isEqualTo("title");
        assertThat(post.getSummary()).isEqualTo("summary");
        assertThat(post.getContent()).isEqualTo("content");
        assertThat(post.getUpdatedAt()).isEqualTo(updatedAt);
    }

    @Test
    public void publish() {
        when(repository.findByIdAndDeletedAtIsNull(POST_ID)).thenReturn(Optional.of(post));
        when(repository.save(any())).then(AdditionalAnswers.returnsFirstArg());
        when(userService.getById(USER_ID)).thenReturn(TEST_USER);
        when(codeApiClient.checkCode(eq(new CodeCheckRequest(TEST_USER.getUuid(), "123456"))))
            .thenReturn(new ApiResult(NO_CONTENT.value(), null));

        OffsetDateTime updatedAt = post.getUpdatedAt();

        service.publish(1L, new PostPublishDto("123456"));

        verify(repository).findByIdAndDeletedAtIsNull(POST_ID);
        verify(repository).save(any());

        assertThat(post.getPublishedAt()).isNotNull();

        assertThat(post.getId()).isEqualTo(POST_ID);
        assertThat(post.getTitle()).isEqualTo("title");
        assertThat(post.getSummary()).isEqualTo("summary");
        assertThat(post.getContent()).isEqualTo("content");
        assertThat(post.getUpdatedAt()).isEqualTo(updatedAt);
    }
}
