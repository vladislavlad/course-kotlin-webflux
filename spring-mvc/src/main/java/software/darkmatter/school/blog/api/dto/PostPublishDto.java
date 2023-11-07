package software.darkmatter.school.blog.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostPublishDto(
    @NotNull
    @Size(min = 6, max = 6)
    String code
) {}
