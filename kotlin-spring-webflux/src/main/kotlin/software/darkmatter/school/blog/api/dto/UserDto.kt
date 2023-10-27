package software.darkmatter.school.blog.api.dto

import java.util.UUID

data class UserDto(
    val id: Long,
    val uuid: UUID?,
    val username: String?,
    val firstName: String?,
    val lastName: String?,
)
