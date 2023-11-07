package software.darkmatter.school.blog.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.darkmatter.school.blog.api.dto.PostCreateDto;
import software.darkmatter.school.blog.api.dto.PostDto;
import software.darkmatter.school.blog.api.dto.PostPublishDto;
import software.darkmatter.school.blog.api.dto.UserDto;
import software.darkmatter.school.blog.domain.post.business.PostService;
import software.darkmatter.school.blog.domain.post.data.Post;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService service;

    @GetMapping
    public Flux<PostDto> list(
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "20") Integer size
    ) {
        return service.getList(Pageable.ofSize(size).withPage(page))
                      .map(this::convertToDto);
    }

    @GetMapping("/{id}")
    public Mono<PostDto> get(@PathVariable Long id) {
        return service.getById(id).map(this::convertToDto);
    }

    @PostMapping
    public Mono<PostDto> create(@Valid @RequestBody PostCreateDto body) {
        return service.create(body).map(this::convertToDto);
    }

    @PutMapping("/{id}")
    public Mono<PostDto> update(@PathVariable Long id, @Valid @RequestBody PostCreateDto body) {
        return service.update(id, body).map(this::convertToDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @PostMapping("/{id}/publish")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<Void> publish(@PathVariable Long id, @Valid @RequestBody PostPublishDto body) {
        return service.publish(id, body);
    }

    private PostDto convertToDto(Post post) {
        UserDto createdBy;
        if (post.getCreatedBy() != null) {
            createdBy = new UserDto(
                post.getCreatedByUserId(),
                post.getCreatedBy().getUuid(),
                post.getCreatedBy().getUsername(),
                post.getCreatedBy().getFirstName(),
                post.getCreatedBy().getLastName()
            );
        } else {
            createdBy = new UserDto(post.getCreatedByUserId(), null, null, null, null);
        }

        return new PostDto(
            post.getId(),
            post.getTitle(),
            post.getSummary(),
            post.getContent(),
            post.getCreatedAt(),
            createdBy,
            post.getUpdatedAt(),
            post.getPublishedAt()
        );
    }
}
