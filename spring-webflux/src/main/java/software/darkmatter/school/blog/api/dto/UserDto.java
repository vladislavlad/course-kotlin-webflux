package software.darkmatter.school.blog.api.dto;

import java.util.UUID;

public record UserDto(
    Long id,
    UUID uuid,
    String username,
    String firstName,
    String lastName
) {}
