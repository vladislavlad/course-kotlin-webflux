package software.darkmatter.school.blog.domain.comment.data

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Table
import software.darkmatter.school.blog.domain.user.domain.User
import java.time.OffsetDateTime

@Table(name = "comments")
data class Comment(
    @Id
    var id: Long? = null,
    val postId: Long,
    var text: String,
    val createdAt: OffsetDateTime,
    val createdByUserId: Long,
    var updatedAt: OffsetDateTime,
    var updatedByUserId: Long,
    var deletedAt: OffsetDateTime?,
    var deletedByUserId: Long?,
) {
    @Transient
    var createdBy: User? = null
}
