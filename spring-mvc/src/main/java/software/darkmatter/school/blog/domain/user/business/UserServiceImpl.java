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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public List<User> getList(Pageable pageable) {
        return repository.findAll(pageable).stream()
                         .collect(Collectors.toList());
    }

    @Override
    public User getById(Long id) {
        return repository.findById(id)
                         .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    @Transactional
    public User createUser(UserCreateDto userCreateDto) {
        User user = new User();
        user.setFirstName(userCreateDto.firstName());
        user.setLastName(userCreateDto.lastName());
        user.setCreatedAt(OffsetDateTime.now());
        user.setUpdatedAt(OffsetDateTime.now());
        return repository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(Long id, UserCreateDto userCreateDto) {
        User user = getById(id);
        user.setFirstName(userCreateDto.firstName());
        user.setLastName(userCreateDto.lastName());
        user.setUpdatedAt(OffsetDateTime.now());
        return repository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }
}