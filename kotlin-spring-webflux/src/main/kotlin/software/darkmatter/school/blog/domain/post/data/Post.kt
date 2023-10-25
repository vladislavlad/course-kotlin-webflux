package software.darkmatter.school.blog.domain.post.data

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Table
import software.darkmatter.school.blog.domain.user.domain.User
import java.time.OffsetDateTime

@Table("posts")
data class Post(
    @Id
    val id: Long? = null,
    var title: String?,
    var summary: String?,
    var content: String?,
    val createdAt: OffsetDateTime,
    val createdByUserId: Long,
    var updatedAt: OffsetDateTime,
    var updatedByUserId: Long,
    var deletedAt: OffsetDateTime?,
    var deletedByUserId: Long?,
    var publishedAt: OffsetDateTime?,
) {

    @Transient
    var createdBy: User? = null
}
