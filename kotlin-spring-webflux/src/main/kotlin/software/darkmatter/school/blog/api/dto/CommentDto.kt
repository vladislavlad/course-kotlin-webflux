package software.darkmatter.school.blog.api.dto

import java.time.OffsetDateTime

data class CommentDto(
    val id: Long,
    val postId: Long,
    val text: String,
    val createdAt: OffsetDateTime,
    val createdBy: UserDto,
    val updatedAt: OffsetDateTime,
)
