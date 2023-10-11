package software.darkmatter.school.blog.api.dto;

public record UserCreateDto(
    String username,
    String firstName,
    String lastName
) {}
