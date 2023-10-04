package software.darkmatter.school.blog.api.dto;

public record CommentCreateDto(
    Long userId,
    Long postId,
    String text
) {}
