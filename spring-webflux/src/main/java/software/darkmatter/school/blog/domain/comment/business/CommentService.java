package software.darkmatter.school.blog.domain.comment.business;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.darkmatter.school.blog.api.dto.CommentCreateDto;
import software.darkmatter.school.blog.api.dto.CommentUpdateDto;
import software.darkmatter.school.blog.domain.comment.data.Comment;

public interface CommentService {

    Flux<Comment> getListByPostId(Long postId, Pageable pageable);

    Mono<Comment> getById(Long id);

    Mono<Comment> getByPostIdAndId(Long postId, Long id);

    Mono<Comment> create(Long postId, CommentCreateDto createDto);

    Mono<Comment> update(Long id, CommentUpdateDto updateDto);

    Mono<Void> delete(Long id);
}
