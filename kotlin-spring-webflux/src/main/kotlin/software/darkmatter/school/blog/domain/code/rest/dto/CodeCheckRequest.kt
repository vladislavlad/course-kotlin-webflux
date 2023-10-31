package software.darkmatter.school.blog.domain.code.rest.dto

import java.util.UUID

data class CodeCheckRequest(
    val userUuid: UUID,
    val code: String,
)
