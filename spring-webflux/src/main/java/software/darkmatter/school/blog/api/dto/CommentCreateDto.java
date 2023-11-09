package software.darkmatter.school.blog.api.dto;

import jakarta.validation.constraints.NotBlank;

public record CommentCreateDto(
    @NotBlank
    String text
) {}
