package software.darkmatter.school.blog.domain.user.business

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import software.darkmatter.school.blog.api.dto.UserCreateDto
import software.darkmatter.school.blog.domain.user.domain.User
import software.darkmatter.school.blog.domain.user.domain.UserRepository
import software.darkmatter.school.blog.domain.user.error.UserNotFoundException
import java.time.OffsetDateTime
import java.util.UUID

@Service
class UserServiceImpl(
    private val repository: UserRepository,
) : UserService {

    override suspend fun getList(pageable: Pageable): List<User> =
        repository.findAllByDeletedAtIsNull(pageable).toList()

    override suspend fun getListByIds(ids: List<Long>): List<User> =
        repository.findAllByIdIn(ids).toList()

    override suspend fun getById(id: Long): User =
        repository.findByIdAndDeletedAtIsNull(id)
            ?: throw UserNotFoundException(id)

    @Transactional
    override suspend fun create(userCreateDto: UserCreateDto): User {
        val user = User(
            id = null,
            uuid = UUID.randomUUID(),
            username = userCreateDto.username!!,
            firstName = userCreateDto.firstName!!,
            lastName = userCreateDto.lastName!!,
            createdAt = OffsetDateTime.now(),
            updatedAt = OffsetDateTime.now(),
            deletedAt = null
        )
        return repository.save(user).awaitSingle()
    }

    @Transactional
    override suspend fun update(id: Long, userCreateDto: UserCreateDto): User =
        getById(id).let { user ->
            user.username = userCreateDto.username!!
            user.firstName = userCreateDto.firstName!!
            user.lastName = userCreateDto.lastName!!
            user.updatedAt = OffsetDateTime.now()
            repository.save(user).awaitSingle()
        }

    @Transactional
    override suspend fun delete(id: Long) {
        return getById(id).let { user ->
            user.deletedAt = OffsetDateTime.now()
            repository.save(user).awaitSingle()
        }
    }
}
