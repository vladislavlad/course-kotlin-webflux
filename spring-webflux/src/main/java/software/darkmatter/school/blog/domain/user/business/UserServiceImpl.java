package software.darkmatter.school.blog.domain.user.business;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
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
    public Flux<User> getList(Pageable pageable) {
        return repository.findAllByDeletedAtIsNull(pageable);
    }

    @Override
    public Flux<User> getListByIds(List<Long> ids) {
        return repository.findAllByIdIn(ids);
    }

    @Override
    public Mono<User> getById(Long id) {
        return repository.findByIdAndDeletedAtIsNull(id)
                         .switchIfEmpty(Mono.error(() -> new UserNotFoundException(id)));
    }

    @Override
    @Transactional
    public Mono<User> create(UserCreateDto userCreateDto) {
        User user = new User();
        user.setUuid(UUID.randomUUID());
        user.setFirstName(userCreateDto.firstName());
        user.setLastName(userCreateDto.lastName());
        OffsetDateTime now = OffsetDateTime.now();
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        return repository.save(user);
    }

    @Override
    @Transactional
    public Mono<User> update(Long id, UserCreateDto userCreateDto) {
        return getById(id).flatMap(user -> {
            user.setUsername(userCreateDto.username());
            user.setFirstName(userCreateDto.firstName());
            user.setLastName(userCreateDto.lastName());
            user.setUpdatedAt(OffsetDateTime.now());
            return repository.save(user);
        });
    }

    @Override
    @Transactional
    public Mono<Void> delete(Long id) {
        return getById(id).flatMap(user -> {
            user.setDeletedAt(OffsetDateTime.now());
            return repository.save(user).then();
        });
    }
}
