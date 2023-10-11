package software.darkmatter.school.blog.domain.user.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {

    Mono<User> findByIdAndDeletedAtIsNull(Long id);

    Flux<User> findAllByDeletedAtIsNull(Pageable pageable);
}
