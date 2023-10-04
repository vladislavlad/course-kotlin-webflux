package software.darkmatter.school.blog.api.dto;

import jakarta.validation.constraints.NotNull;

public record CommentCreateDto(
    @NotNull
    Long userId,
    @NotNull
    String text
) {}
