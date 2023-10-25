package software.darkmatter.school.blog.api.dto

import java.time.OffsetDateTime

data class PostDto(
    val id: Long,
    val title: String?,
    val summary: String?,
    val content: String?,
    val createdAt: OffsetDateTime,
    val createdBy: UserDto,
    val updatedAt: OffsetDateTime,
    val publishedAt: OffsetDateTime?
)
