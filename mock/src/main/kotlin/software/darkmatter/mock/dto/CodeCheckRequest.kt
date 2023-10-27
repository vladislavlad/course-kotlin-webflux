package software.darkmatter.mock.dto

import java.util.UUID

data class CodeCheckRequest(
    val userUuid: UUID,
    val code: String,
)
