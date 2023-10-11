package software.darkmatter.school.blog.api.dto;

import java.time.OffsetDateTime;

public record PostDto(
    Long id,
    String title,
    String summary,
    String text,
    OffsetDateTime createdAt,
    UserDto createdBy,
    OffsetDateTime updatedAt,
    OffsetDateTime publishedAt
) {}
