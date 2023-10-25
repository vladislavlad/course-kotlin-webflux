package software.darkmatter.school.blog.domain.comment.business;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.darkmatter.school.blog.api.dto.CommentCreateDto;
import software.darkmatter.school.blog.api.dto.CommentUpdateDto;
import software.darkmatter.school.blog.domain.comment.data.Comment;
import software.darkmatter.school.blog.domain.comment.data.CommentRepository;
import software.darkmatter.school.blog.domain.comment.error.CommentNotFoundException;
import software.darkmatter.school.blog.domain.user.business.UserService;

import java.time.OffsetDateTime;

import static software.darkmatter.school.blog.config.security.SimpleAuthentication.simpleAuthFromContext;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;
    private final UserService userService;

    @Override
    public Flux<Comment> getListByPostId(Long postId, Pageable pageable) {
        return repository.findAllByPostIdAndDeletedAtIsNull(postId, pageable);
    }

    @Override
    public Mono<Comment> getById(Long id) {
        return repository.findByIdAndDeletedAtIsNull(id)
                         .switchIfEmpty(
                             Mono.error(() -> new CommentNotFoundException(id))
                         ).flatMap(post ->
                                       userService.getById(post.getCreatedByUserId())
                                                  .map(
                                                      user -> {
                                                          post.setCreatedBy(user);
                                                          return post;
                                                      }
                                                  )
            );
    }

    @Override
    public Mono<Comment> getByPostIdAndId(Long postId, Long id) {
        return repository.findByPostIdAndIdAndDeletedAtIsNull(postId, id);
    }

    @Override
    public Mono<Comment> create(Long postId, CommentCreateDto createDto) {
        return simpleAuthFromContext().flatMap(
            authentication ->
                userService.getById(authentication.getUserId())
                           .flatMap(user -> {
                               var comment = new Comment();
                               comment.setPostId(postId);
                               comment.setText(createDto.text());
                               OffsetDateTime now = OffsetDateTime.now();
                               comment.setCreatedAt(now);
                               comment.setCreatedByUserId(user.getId());
                               comment.setUpdatedAt(now);
                               comment.setUpdatedByUserId(user.getId());
                               return repository.save(comment);
                           })
        );
    }

    @Override
    public Mono<Comment> update(Long id, CommentUpdateDto updateDto) {
        return simpleAuthFromContext().flatMap(
            authentication ->
                Mono.zip(
                    userService.getById(authentication.getUserId()),
                    getById(id)
                ).flatMap(pair -> {
                    var user = pair.getT1();
                    var comment = pair.getT2();

                    comment.setText(updateDto.text());
                    comment.setUpdatedAt(OffsetDateTime.now());
                    comment.setUpdatedByUserId(user.getId());
                    return repository.save(comment);
                })
        );
    }

    @Override
    public Mono<Void> delete(Long id) {
        return simpleAuthFromContext().flatMap(
            authentication ->
                Mono.zip(
                    userService.getById(authentication.getUserId()),
                    getById(id)
                ).flatMap((pair) -> {
                    var user = pair.getT1();
                    var comment = pair.getT2();

                    comment.setDeletedAt(OffsetDateTime.now());
                    comment.setDeletedByUserId(user.getId());
                    return repository.save(comment);
                }).then()
        );
    }
}
