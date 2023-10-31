package software.darkmatter.school.blog.domain.user.business

import org.springframework.data.domain.Pageable
import software.darkmatter.school.blog.api.dto.UserCreateDto
import software.darkmatter.school.blog.domain.user.domain.User

interface UserService {
    suspend fun getList(pageable: Pageable): List<User>
    suspend fun getListByIds(ids: List<Long>): List<User>
    suspend fun getById(id: Long): User
    suspend fun create(userCreateDto: UserCreateDto): User
    suspend fun update(id: Long, userCreateDto: UserCreateDto): User
    suspend fun delete(id: Long)
}
