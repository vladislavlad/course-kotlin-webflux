package software.darkmatter.school.blog.domain.user.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.OffsetDateTime
import java.util.UUID

@Table("users")
data class User(
    @Id
    val id: Long? = null,
    val uuid: UUID,
    var username: String,
    var firstName: String,
    var lastName: String,
    val createdAt: OffsetDateTime,
    var updatedAt: OffsetDateTime,
    var deletedAt: OffsetDateTime?,
)
