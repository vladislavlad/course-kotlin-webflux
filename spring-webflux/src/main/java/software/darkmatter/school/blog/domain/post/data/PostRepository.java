package software.darkmatter.school.blog.domain.post.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostRepository extends ReactiveCrudRepository<Post, Long> {

    Mono<Post> findByIdAndDeletedAtIsNull(Long id);

    Flux<Post> findAllByDeletedAtIsNull(Pageable pageable);
}
