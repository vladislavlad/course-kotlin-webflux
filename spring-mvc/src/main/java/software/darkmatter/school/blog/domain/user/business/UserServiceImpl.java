package software.darkmatter.school.blog.domain.user.business;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.darkmatter.school.blog.api.dto.UserCreateDto;
import software.darkmatter.school.blog.domain.user.data.User;
import software.darkmatter.school.blog.domain.user.data.UserRepository;
import software.darkmatter.school.blog.domain.user.error.UserNotFoundException;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public List<User> getList(Pageable pageable) {
        return repository.findAllByDeletedAtIsNull(pageable);
    }

    @Override
    public User getById(Long id) {
        return repository.findByIdAndDeletedAtIsNull(id)
                         .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    @Transactional
    public User create(UserCreateDto userCreateDto) {
        User user = new User();
        user.setUuid(UUID.randomUUID());
        user.setFirstName(userCreateDto.firstName());
        user.setLastName(userCreateDto.lastName());
        user.setCreatedAt(OffsetDateTime.now());
        user.setUpdatedAt(OffsetDateTime.now());
        return repository.save(user);
    }

    @Override
    @Transactional
    public User update(Long id, UserCreateDto userCreateDto) {
        User user = getById(id);
        user.setUsername(userCreateDto.username());
        user.setFirstName(userCreateDto.firstName());
        user.setLastName(userCreateDto.lastName());
        user.setUpdatedAt(OffsetDateTime.now());
        return repository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User user = getById(id);
        user.setDeletedAt(OffsetDateTime.now());
        repository.save(user);
    }
}
