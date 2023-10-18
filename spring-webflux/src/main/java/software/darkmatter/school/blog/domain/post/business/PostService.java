package software.darkmatter.school.blog.domain.post.business;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.darkmatter.school.blog.api.dto.PostCreateDto;
import software.darkmatter.school.blog.domain.post.data.Post;

public interface PostService {

    Flux<Post> getList(Pageable pageable);

    Mono<Post> getById(Long id);

    Mono<Post> create(PostCreateDto postCreateDto);

    Mono<Post> update(Long id, PostCreateDto postCreateDto);

    Mono<Void> delete(Long id);

    Mono<Void> publish(Long id);
}
