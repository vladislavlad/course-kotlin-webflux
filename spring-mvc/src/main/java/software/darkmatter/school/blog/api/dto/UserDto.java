package software.darkmatter.school.blog.api.dto;

public record UserDto(
    Long id,
    String username,
    String firstName,
    String lastName
) {}
