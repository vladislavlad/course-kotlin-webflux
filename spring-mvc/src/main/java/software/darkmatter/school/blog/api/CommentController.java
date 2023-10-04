package software.darkmatter.school.blog.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import software.darkmatter.school.blog.api.dto.CommentCreateDto;
import software.darkmatter.school.blog.api.dto.CommentDto;
import software.darkmatter.school.blog.api.dto.CommentUpdateDto;
import software.darkmatter.school.blog.api.dto.UserDto;
import software.darkmatter.school.blog.domain.comment.business.CommentService;
import software.darkmatter.school.blog.domain.comment.data.Comment;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService service;

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> list(
        @PathVariable Long postId,
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "20") Integer size
    ) {
        return service.getList(Pageable.ofSize(size).withPage(page))
                      .stream()
                      .map(this::convertToDto)
                      .collect(Collectors.toList());
    }

    @GetMapping("/comments/{id}")
    public CommentDto getByPostIdAndId(@PathVariable Long id) {
        return convertToDto(service.getById(id));
    }

    @GetMapping("/posts/{postId}/comments/{id}")
    public CommentDto getByPostIdAndId(
        @PathVariable Long postId,
        @PathVariable Long id
    ) {
        return convertToDto(service.getByPostIdAndId(postId, id));
    }

    @PostMapping("/posts/{postId}/comments")
    public CommentDto create(@RequestBody CommentCreateDto commentCreateDto) {
        return convertToDto(service.create(commentCreateDto));
    }

    @PutMapping("/comments/{id}")
    public CommentDto update(@PathVariable Long id, @RequestBody CommentUpdateDto updateDto) {
        return convertToDto(service.update(id, updateDto));
    }

    private CommentDto convertToDto(Comment comment) {
        return new CommentDto(
            comment.getText(),
            comment.getCreatedAt(),
            new UserDto(
                comment.getCreatedBy().getId(),
                comment.getCreatedBy().getFirstName(),
                comment.getCreatedBy().getLastName()
            ),
            comment.getUpdatedAt()
        );
    }
}
