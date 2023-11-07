package software.darkmatter.school.blog.domain.code.rest.dto;

import java.util.UUID;

public record CodeCheckRequest(UUID userUuid, String code) {}
