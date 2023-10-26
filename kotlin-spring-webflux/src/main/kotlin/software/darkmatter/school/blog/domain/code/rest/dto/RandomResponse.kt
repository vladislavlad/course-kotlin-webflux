package software.darkmatter.school.blog.domain.code.rest.dto

import java.util.UUID

data class RandomResponse(
    val code: String,
    val uuid: UUID,
)
