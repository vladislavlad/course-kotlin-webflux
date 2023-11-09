package software.darkmatter.school.blog.api.dto

import jakarta.validation.constraints.NotBlank

data class CommentUpdateDto(
    @field:NotBlank
    val text: String?,
)
