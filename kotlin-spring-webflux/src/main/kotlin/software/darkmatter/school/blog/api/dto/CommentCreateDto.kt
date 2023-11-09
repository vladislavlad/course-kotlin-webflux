package software.darkmatter.school.blog.api.dto

import jakarta.validation.constraints.NotBlank

data class CommentCreateDto(
    @field:NotBlank val text: String?,
)
