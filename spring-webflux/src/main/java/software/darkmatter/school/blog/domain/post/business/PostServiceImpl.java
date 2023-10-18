package software.darkmatter.school.blog.domain.post.business;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.darkmatter.school.blog.api.dto.PostCreateDto;
import software.darkmatter.school.blog.domain.post.data.Post;
import software.darkmatter.school.blog.domain.post.data.PostRepository;
import software.darkmatter.school.blog.domain.post.error.PostNotFoundException;
import software.darkmatter.school.blog.domain.user.business.UserService;

import java.time.OffsetDateTime;

import static software.darkmatter.school.blog.config.security.SimpleAuthentication.simpleAuthFromContext;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository repository;
    private final UserService userService;

    @Override
    public Flux<Post> getList(Pageable pageable) {
        return repository.findAllByDeletedAtIsNull(pageable);
    }

    @Override
    public Mono<Post> getById(Long id) {
        return repository.findByIdAndDeletedAtIsNull(id).switchIfEmpty(
            Mono.error(() -> new PostNotFoundException(id))
        );
    }

    @Override
    @Transactional
    public Mono<Post> create(PostCreateDto postCreateDto) {
        var authentication = simpleAuthFromContext();
        return userService.getById(authentication.getUserId())
                          .flatMap(user -> {
                              var post = new Post();
                              post.setTitle(postCreateDto.title());
                              post.setSummary(postCreateDto.summary());
                              post.setContent(postCreateDto.content());
                              post.setCreatedAt(OffsetDateTime.now());
                              post.setCreatedByUserId(user.getId());
                              post.setUpdatedAt(OffsetDateTime.now());
                              post.setUpdatedByUserId(user.getId());
                              return repository.save(post);
                          });
    }

    @Override
    @Transactional
    public Mono<Post> update(Long id, PostCreateDto postCreateDto) {
        var authentication = simpleAuthFromContext();
        return Mono.zip(
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
        });
    }

    @Override
    @Transactional
    public Mono<Void> publish(Long id) {
        return getById(id)
            .flatMap(post -> {
                post.setUpdatedAt(OffsetDateTime.now());
                return repository.save(post);
            })
            .then();
    }

    @Override
    @Transactional
    public Mono<Void> delete(Long id) {
        var authentication = simpleAuthFromContext();
        return Mono.zip(
            userService.getById(authentication.getUserId()),
            getById(id)
        ).flatMap((pair) -> {
            var user = pair.getT1();
            var post = pair.getT2();

            post.setDeletedAt(OffsetDateTime.now());
            post.setDeletedByUserId(user.getId());
            return repository.save(post);
        }).then();
    }
}
