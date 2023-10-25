package software.darkmatter.school.blog.domain.post.business

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import software.darkmatter.school.blog.api.dto.PostCreateDto
import software.darkmatter.school.blog.config.security.SimpleAuthentication.Companion.simpleAuthFromContext
import software.darkmatter.school.blog.domain.post.data.Post
import software.darkmatter.school.blog.domain.post.data.PostRepository
import software.darkmatter.school.blog.domain.post.error.PostNotFoundException
import software.darkmatter.school.blog.domain.user.business.UserService
import java.time.OffsetDateTime

@Service
class PostServiceImpl(
    private val repository: PostRepository,
    private val userService: UserService,
) : PostService {

    override suspend fun getList(pageable: Pageable): List<Post> =
        repository.findAllByDeletedAtIsNull(pageable).toList()

    override suspend fun getById(id: Long): Post {
        val post = repository.findByIdAndDeletedAtIsNull(id) ?: throw PostNotFoundException(id)
        post.createdBy = userService.getById(post.createdByUserId)

        return post
    }

    @Transactional
    override suspend fun create(postCreateDto: PostCreateDto): Post {
        return simpleAuthFromContext()
            .let { authentication ->
                userService.getById(authentication.userId)
                    .let { user ->
                        val createdAt = OffsetDateTime.now()
                        val post = Post(
                            title = postCreateDto.title,
                            summary = postCreateDto.summary,
                            content = postCreateDto.content,
                            createdAt = createdAt,
                            createdByUserId = user.id!!,
                            updatedAt = createdAt,
                            updatedByUserId = user.id,
                            deletedAt = null,
                            deletedByUserId = null,
                            publishedAt = null
                        )
                        repository.save(post).awaitSingle()
                    }
            }
    }

    @Transactional
    override suspend fun update(id: Long, postCreateDto: PostCreateDto): Post {
        val authentication = simpleAuthFromContext()
        val user = userService.getById(authentication.userId)
        val post = getById(id)

        post.title = postCreateDto.title
        post.summary = postCreateDto.summary
        post.content = postCreateDto.content
        post.updatedAt = OffsetDateTime.now()
        post.updatedByUserId = user.id!!

        return repository.save(post).awaitSingle()
    }

    @Transactional
    override suspend fun publish(id: Long) {
        val post = getById(id)

        post.publishedAt = OffsetDateTime.now()
        repository.save(post).awaitSingle()
    }

    @Transactional
    override suspend fun delete(id: Long) {
        val authentication = simpleAuthFromContext()
        val user = userService.getById(authentication.userId)
        val post = getById(id)

        post.deletedAt = OffsetDateTime.now()
        post.deletedByUserId = user.id!!
        repository.save(post).awaitSingle()
    }
}
