package software.darkmatter.school.blog.domain.comment.business

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import software.darkmatter.school.blog.api.dto.CommentCreateDto
import software.darkmatter.school.blog.api.dto.CommentUpdateDto
import software.darkmatter.school.blog.config.security.SimpleAuthentication.Companion.simpleAuthFromContext
import software.darkmatter.school.blog.domain.comment.data.Comment
import software.darkmatter.school.blog.domain.comment.data.CommentRepository
import software.darkmatter.school.blog.domain.comment.error.CommentNotFoundException
import software.darkmatter.school.blog.domain.post.business.PostService
import software.darkmatter.school.blog.domain.user.business.UserService
import java.time.OffsetDateTime

@Service
class CommentServiceImpl(
    private val repository: CommentRepository,
    private val userService: UserService,
    private val postService: PostService
) : CommentService {

    override suspend fun getListByPostId(postId: Long, pageable: Pageable): List<Comment> =
        repository.findAllByPostIdAndDeletedAtIsNull(postId, pageable).toList()

    override suspend fun getById(id: Long): Comment {
        val comment = repository.findByIdAndDeletedAtIsNull(id) ?: throw CommentNotFoundException(id)
        comment.createdBy = userService.getById(comment.createdByUserId)

        return comment
    }

    override suspend fun getByPostIdAndId(postId: Long, id: Long): Comment {
        val comment = repository.findByPostIdAndIdAndDeletedAtIsNull(postId, id) ?: throw CommentNotFoundException(id)
        comment.createdBy = userService.getById(comment.createdByUserId)

        return comment
    }

    override suspend fun create(postId: Long, createDto: CommentCreateDto): Comment {
        postService.getById(postId)
        val authentication = simpleAuthFromContext()
        val user = userService.getById(authentication.userId)
        val createdAt = OffsetDateTime.now()

        val comment = Comment(
            postId = postId,
            text = createDto.text!!,
            createdAt = createdAt,
            createdByUserId = user.id!!,
            updatedAt = createdAt,
            updatedByUserId = user.id,
            deletedAt = null,
            deletedByUserId = null
        )

        return repository.save(comment).awaitSingle()
    }

    override suspend fun update(id: Long, updateDto: CommentUpdateDto): Comment {
        val authentication = simpleAuthFromContext()
        val user = userService.getById(authentication.userId)
        val comment = getById(id)

        comment.text = updateDto.text!!
        comment.updatedAt = OffsetDateTime.now()
        comment.updatedByUserId = user.id!!

        return repository.save(comment).awaitSingle()
    }

    override suspend fun delete(id: Long) {
        val authentication = simpleAuthFromContext()
        val user = userService.getById(authentication.userId)
        val comment = getById(id)

        comment.deletedAt = OffsetDateTime.now()
        comment.deletedByUserId = user.id!!

        repository.save(comment).awaitSingle()
    }
}
