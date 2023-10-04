package software.darkmatter.school.blog.api.dto;

import java.time.OffsetDateTime;

public record CommentDto(
    String text,
    OffsetDateTime createdAt,
    UserDto createdBy,
    OffsetDateTime updatedAt
) {}
