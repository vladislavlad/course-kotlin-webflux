package software.darkmatter.school.blog.api.dto;

import jakarta.validation.constraints.NotNull;

public record PostCreateDto(
    @NotNull
    Long userId,
    @NotNull
    String title,
    String summary,
    @NotNull
    String content
) {}
