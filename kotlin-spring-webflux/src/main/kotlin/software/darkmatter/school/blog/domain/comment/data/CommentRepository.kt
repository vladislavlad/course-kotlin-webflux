package software.darkmatter.school.blog.domain.comment.data

import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface CommentRepository : ReactiveCrudRepository<Comment, Long> {

    suspend fun findByIdAndDeletedAtIsNull(id: Long?): Comment?

    fun findAllByPostIdAndDeletedAtIsNull(postId: Long, pageable: Pageable): Flow<Comment>

    suspend fun findByPostIdAndIdAndDeletedAtIsNull(postId: Long, id: Long): Comment?
}
