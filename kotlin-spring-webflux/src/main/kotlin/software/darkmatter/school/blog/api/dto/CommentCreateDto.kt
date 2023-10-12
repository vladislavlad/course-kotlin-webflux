package software.darkmatter.school.blog.api.dto

import jakarta.validation.constraints.NotNull

data class CommentCreateDto(
    @field:NotNull
    val text: String?,
)
