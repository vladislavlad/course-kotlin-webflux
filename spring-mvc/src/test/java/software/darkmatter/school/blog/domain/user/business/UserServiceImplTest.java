package software.darkmatter.school.blog.domain.user.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import software.darkmatter.school.blog.api.dto.UserCreateDto;
import software.darkmatter.school.blog.domain.user.data.User;
import software.darkmatter.school.blog.domain.user.data.UserRepository;
import software.darkmatter.school.blog.domain.user.error.UserNotFoundException;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    private final UserRepository repository = mock();

    private final UserService service = new UserServiceImpl(repository);

    private static final Long USER_ID = 1L;

    private User user;

    @BeforeEach
    public void setUp() {
        OffsetDateTime now = OffsetDateTime.now();

        user = new User();
        user.setId(USER_ID);
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
    }

    @Test
    public void getByIdNotFound() {
        when(repository.findByIdAndDeletedAtIsNull(USER_ID)).thenReturn(Optional.of(user));
        when(repository.findById(2L)).thenThrow(new UserNotFoundException(2L));

        User found = service.getById(USER_ID);

        verify(repository).findByIdAndDeletedAtIsNull(USER_ID);
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(USER_ID);

        assertThrows(UserNotFoundException.class, () -> service.getById(2L));
        verify(repository).findByIdAndDeletedAtIsNull(2L);
    }

    @Test
    public void update() {
        when(repository.findByIdAndDeletedAtIsNull(USER_ID)).thenReturn(Optional.of(user));
        when(repository.save(any())).then(AdditionalAnswers.returnsFirstArg());

        OffsetDateTime updatedAt = user.getUpdatedAt();

        User updated = service.update(1L, new UserCreateDto("upd firstName", "upd lastName"));

        verify(repository).findByIdAndDeletedAtIsNull(USER_ID);
        verify(repository).save(any());

        assertThat(updated.getId()).isEqualTo(USER_ID);
        assertThat(updated.getFirstName()).isEqualTo("upd firstName");
        assertThat(updated.getLastName()).isEqualTo("upd lastName");
        assertThat(updated.getUpdatedAt()).isNotEqualTo(updatedAt);
    }
}
