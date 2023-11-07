package software.darkmatter.school.blog.domain.post.business;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.darkmatter.school.blog.api.dto.PostCreateDto;
import software.darkmatter.school.blog.api.dto.PostPublishDto;
import software.darkmatter.school.blog.domain.code.rest.CodeApiClient;
import software.darkmatter.school.blog.domain.code.rest.dto.CodeCheckRequest;
import software.darkmatter.school.blog.domain.post.data.Post;
import software.darkmatter.school.blog.domain.post.data.PostRepository;
import software.darkmatter.school.blog.domain.post.error.PostAlreadyPublishedException;
import software.darkmatter.school.blog.domain.post.error.PostNotFoundException;
import software.darkmatter.school.blog.domain.user.business.UserService;
import software.darkmatter.school.blog.error.NotAuthorizedException;

import java.time.OffsetDateTime;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static software.darkmatter.school.blog.config.security.SimpleAuthentication.simpleAuthFromContext;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository repository;
    private final UserService userService;
    private final CodeApiClient codeApiClient;

    @Override
    public Flux<Post> getList(Pageable pageable) {
        return repository.findAllByDeletedAtIsNull(pageable);
    }

    @Override
    public Mono<Post> getById(Long id) {
        return repository.findByIdAndDeletedAtIsNull(id)
                         .switchIfEmpty(
                             Mono.error(() -> new PostNotFoundException(id))
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
    @Transactional
    public Mono<Post> create(PostCreateDto postCreateDto) {
        return simpleAuthFromContext()
            .flatMap(
                authentication ->
                    userService.getById(authentication.getUserId())
                               .flatMap(user -> {
                                   var post = new Post();
                                   post.setTitle(postCreateDto.title());
                                   post.setSummary(postCreateDto.summary());
                                   post.setContent(postCreateDto.content());
                                   OffsetDateTime now = OffsetDateTime.now();
                                   post.setCreatedAt(now);
                                   post.setCreatedByUserId(user.getId());
                                   post.setUpdatedAt(now);
                                   post.setUpdatedByUserId(user.getId());
                                   return repository.save(post);
                               })
            );
    }

    @Override
    @Transactional
    public Mono<Post> update(Long id, PostCreateDto postCreateDto) {
        return simpleAuthFromContext().flatMap(
            authentication ->
                Mono.zip(
                    userService.getById(authentication.getUserId()),
                    getById(id)
                ).flatMap((pair) -> {
                    var user = pair.getT1();
                    var post = pair.getT2();

                    post.setTitle(postCreateDto.title());
                    post.setSummary(postCreateDto.summary());
                    post.setContent(postCreateDto.content());
                    post.setUpdatedAt(OffsetDateTime.now());
                    post.setUpdatedByUserId(user.getId());
                    return repository.save(post);
                })
        );
    }

    @Override
    @Transactional
    public Mono<Void> publish(Long id, PostPublishDto postPublishDto) {
        return simpleAuthFromContext().flatMap(
            authentication ->
                Mono.zip(
                    userService.getById(authentication.getUserId()),
                    getById(id)
                ).flatMap((pair) -> {
                    var user = pair.getT1();
                    var post = pair.getT2();

                    if (post.getPublishedAt() != null) {
                        return Mono.error(new PostAlreadyPublishedException());
                    }

                    if (!post.getCreatedByUserId().equals(user.getId())) {
                        return Mono.error(new NotAuthorizedException("You are not allowed to publish this post"));
                    }

                    return codeApiClient.checkCode(new CodeCheckRequest(user.getUuid(), postPublishDto.code()))
                                        .flatMap(
                                            codeCheckResult -> {
                                                if (codeCheckResult.status() != NO_CONTENT.code()) {
                                                    return Mono.error(new NotAuthorizedException("Invalid code"));
                                                }

                                                post.setPublishedAt(OffsetDateTime.now());
                                                return repository.save(post);
                                            }
                                        );
                }).then()
        );
    }

    @Override
    @Transactional
    public Mono<Void> delete(Long id) {
        return simpleAuthFromContext().flatMap(
            authentication ->
                Mono.zip(
                    userService.getById(authentication.getUserId()),
                    getById(id)
                ).flatMap((pair) -> {
                    var user = pair.getT1();
                    var post = pair.getT2();

                    post.setDeletedAt(OffsetDateTime.now());
                    post.setDeletedByUserId(user.getId());
                    return repository.save(post);
                }).then()
        );
    }
}
