package software.darkmatter.school.blog.api.dto;

import java.time.OffsetDateTime;

public record CommentDto(
    Long id,
    String text,
    OffsetDateTime createdAt,
    UserDto createdBy,
    OffsetDateTime updatedAt
) {}
