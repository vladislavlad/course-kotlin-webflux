package software.darkmatter.school.blog.api.dto

import jakarta.validation.constraints.NotNull

data class UserCreateDto(
    @field:NotNull
    val username: String?,
    @field:NotNull
    val firstName: String?,
    @field:NotNull
    val lastName: String?
)
