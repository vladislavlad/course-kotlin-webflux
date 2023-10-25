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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.darkmatter.school.blog.api.dto.CommentCreateDto;
import software.darkmatter.school.blog.api.dto.CommentDto;
import software.darkmatter.school.blog.api.dto.CommentUpdateDto;
import software.darkmatter.school.blog.api.dto.UserDto;
import software.darkmatter.school.blog.domain.comment.business.CommentService;
import software.darkmatter.school.blog.domain.comment.data.Comment;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService service;

    @GetMapping("/posts/{postId}/comments")
    public Flux<CommentDto> getListByPost(
        @PathVariable Long postId,
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "20") Integer size
    ) {
        return service.getListByPostId(postId, Pageable.ofSize(size).withPage(page))
                      .map(this::convertToDto);
    }

    @GetMapping("/comments/{id}")
    public Mono<CommentDto> getByPostIdAndId(@PathVariable Long id) {
        return service.getById(id).map(this::convertToDto);
    }

    @GetMapping("/posts/{postId}/comments/{id}")
    public Mono<CommentDto> getByPostIdAndId(
        @PathVariable Long postId,
        @PathVariable Long id
    ) {
        return service.getByPostIdAndId(postId, id).map(this::convertToDto);
    }

    @PostMapping("/posts/{postId}/comments")
    public Mono<CommentDto> create(@PathVariable Long postId, @Valid @RequestBody CommentCreateDto commentCreateDto) {
        return service.create(postId, commentCreateDto).map(this::convertToDto);
    }

    @PutMapping("/comments/{id}")
    public Mono<CommentDto> update(@PathVariable Long id, @RequestBody CommentUpdateDto updateDto) {
        return service.update(id, updateDto).map(this::convertToDto);
    }

    @DeleteMapping("/comments/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable Long id) {
        return service.delete(id);
    }

    private CommentDto convertToDto(Comment comment) {
        UserDto createdBy;
        if (comment.getCreatedBy() != null) {
            createdBy = new UserDto(
                comment.getCreatedByUserId(),
                comment.getCreatedBy().getUsername(),
                comment.getCreatedBy().getFirstName(),
                comment.getCreatedBy().getLastName()
            );
        } else {
            createdBy = new UserDto(comment.getCreatedByUserId(), null, null, null);
        }

        return new CommentDto(
            comment.getId(),
            comment.getText(),
            comment.getCreatedAt(),
            createdBy,
            comment.getUpdatedAt()
        );
    }
}