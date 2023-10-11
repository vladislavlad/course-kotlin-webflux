package software.darkmatter.school.blog.domain.user.business;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.darkmatter.school.blog.api.dto.UserCreateDto;
import software.darkmatter.school.blog.domain.user.domain.User;

public interface UserService {

    Flux<User> getList(Pageable pageable);

    Mono<User> getById(Long id);

    Mono<User> create(UserCreateDto userCreateDto);

    Mono<User> update(Long id, UserCreateDto userCreateDto);

    Mono<Void> delete(Long id);
}
