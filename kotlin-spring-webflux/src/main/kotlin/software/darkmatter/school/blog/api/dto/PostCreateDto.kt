package software.darkmatter.school.blog.api.dto

import jakarta.validation.constraints.NotNull

data class PostCreateDto(
    @field:NotNull
    val title: String?,
    val summary: String?,
    @field:NotNull
    val content: String?,
)
