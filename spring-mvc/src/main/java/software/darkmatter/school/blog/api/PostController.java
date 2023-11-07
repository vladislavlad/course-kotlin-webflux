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
import software.darkmatter.school.blog.api.dto.PostCreateDto;
import software.darkmatter.school.blog.api.dto.PostDto;
import software.darkmatter.school.blog.api.dto.PostPublishDto;
import software.darkmatter.school.blog.api.dto.UserDto;
import software.darkmatter.school.blog.domain.post.business.PostService;
import software.darkmatter.school.blog.domain.post.data.Post;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService service;

    @GetMapping
    public List<PostDto> list(
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "20") Integer size
    ) {
        return service.getList(Pageable.ofSize(size).withPage(page))
                      .stream()
                      .map(this::convertToDto)
                      .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PostDto get(@PathVariable Long id) {
        return convertToDto(service.getById(id));
    }

    @PostMapping
    public PostDto create(@Valid @RequestBody PostCreateDto body) {
        return convertToDto(service.create(body));
    }

    @PutMapping("/{id}")
    public PostDto update(@PathVariable Long id, @Valid @RequestBody PostCreateDto body) {
        return convertToDto(service.update(id, body));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PostMapping("/{id}/publish")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void publish(@PathVariable Long id, @Valid @RequestBody PostPublishDto body) {
        service.publish(id, body);
    }

    private PostDto convertToDto(Post post) {
        return new PostDto(
            post.getId(),
            post.getTitle(),
            post.getSummary(),
            post.getContent(),
            post.getCreatedAt(),
            new UserDto(
                post.getCreatedBy().getId(),
                post.getCreatedBy().getUuid(),
                post.getCreatedBy().getUsername(),
                post.getCreatedBy().getFirstName(),
                post.getCreatedBy().getLastName()
            ),
            post.getUpdatedAt(),
            post.getPublishedAt()
        );
    }
}
