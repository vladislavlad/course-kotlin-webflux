package software.darkmatter.school.blog.domain.post.data

import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface PostRepository : ReactiveCrudRepository<Post, Long> {

    suspend fun findByIdAndDeletedAtIsNull(id: Long): Post?

    fun findAllByDeletedAtIsNull(pageable: Pageable): Flow<Post>
}
