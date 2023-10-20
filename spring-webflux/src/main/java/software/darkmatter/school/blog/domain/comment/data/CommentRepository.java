package software.darkmatter.school.blog.domain.comment.data;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CommentRepository extends ReactiveCrudRepository<Comment, Long> {

    Mono<Comment> findByIdAndDeletedAtIsNull(Long id);

    Flux<Comment> findAllByPostIdAndDeletedAtIsNull(Long postId, Pageable pageable);
}