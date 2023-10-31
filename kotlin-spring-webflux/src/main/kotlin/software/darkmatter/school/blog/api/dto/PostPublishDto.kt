package software.darkmatter.school.blog.api.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class PostPublishDto(
    @field:NotNull
    @field:Size(min = 6, max = 6)
    val code: String?,
)
