package software.darkmatter.school.blog.api.dto;

import jakarta.validation.constraints.NotNull;

public record UserCreateDto(
    @NotNull
    String username,
    @NotNull
    String firstName,
    @NotNull
    String lastName
) {}
