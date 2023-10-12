package software.darkmatter.school.blog.domain.user.domain

import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface UserRepository : ReactiveCrudRepository<User, Long> {

    suspend fun findByIdAndDeletedAtIsNull(id: Long?): User?

    fun findAllByDeletedAtIsNull(pageable: Pageable): Flow<User>
}
