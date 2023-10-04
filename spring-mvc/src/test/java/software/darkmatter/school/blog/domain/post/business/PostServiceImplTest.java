package software.darkmatter.school.blog.domain.post.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import software.darkmatter.school.blog.api.dto.PostCreateDto;
import software.darkmatter.school.blog.domain.post.data.Post;
import software.darkmatter.school.blog.domain.post.data.PostRepository;
import software.darkmatter.school.blog.domain.post.error.PostNotFoundException;
import software.darkmatter.school.blog.domain.user.business.UserService;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PostServiceImplTest {

    private final PostRepository repository = mock();

    private final UserService userService = mock();

    private final PostService service = new PostServiceImpl(repository, userService);

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
        post.setUpdatedAt(now);
        post.setPublishedAt(null);
    }

    @Test
    public void getByIdNotFound() {
        when(repository.findById(POST_ID)).thenReturn(Optional.of(post));
        when(repository.findById(2L)).thenThrow(new PostNotFoundException(2L));

        Post found = service.getById(POST_ID);

        verify(repository).findById(POST_ID);
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(POST_ID);

        assertThrows(PostNotFoundException.class, () -> service.getById(2L));
        verify(repository).findById(2L);
    }

    @Test
    public void update() {
        when(repository.findById(POST_ID)).thenReturn(Optional.of(post));
        when(repository.save(any())).then(AdditionalAnswers.returnsFirstArg());

        OffsetDateTime updatedAt = post.getUpdatedAt();

        Post updated = service.update(1L, new PostCreateDto(USER_ID, "upd title", "upd summary", "upd content"));

        verify(repository).findById(POST_ID);
        verify(repository).save(any());

        assertThat(updated.getId()).isEqualTo(POST_ID);
        assertThat(updated.getTitle()).isEqualTo("upd title");
        assertThat(updated.getSummary()).isEqualTo("upd summary");
        assertThat(updated.getContent()).isEqualTo("upd content");
        assertThat(updated.getUpdatedAt()).isNotEqualTo(updatedAt);
    }

    @Test
    public void delete() {
        when(repository.findById(POST_ID)).thenReturn(Optional.of(post));
        when(repository.save(any())).then(AdditionalAnswers.returnsFirstArg());

        OffsetDateTime updatedAt = post.getUpdatedAt();

        service.delete(1L);

        verify(repository).findById(POST_ID);
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
        when(repository.findById(POST_ID)).thenReturn(Optional.of(post));
        when(repository.save(any())).then(AdditionalAnswers.returnsFirstArg());

        OffsetDateTime updatedAt = post.getUpdatedAt();

        service.publish(1L);

        verify(repository).findById(POST_ID);
        verify(repository).save(any());

        assertThat(post.getPublishedAt()).isNotNull();

        assertThat(post.getId()).isEqualTo(POST_ID);
        assertThat(post.getTitle()).isEqualTo("title");
        assertThat(post.getSummary()).isEqualTo("summary");
        assertThat(post.getContent()).isEqualTo("content");
        assertThat(post.getUpdatedAt()).isEqualTo(updatedAt);
    }
}
