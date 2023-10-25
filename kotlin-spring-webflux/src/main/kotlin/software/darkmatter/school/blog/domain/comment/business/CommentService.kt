package software.darkmatter.school.blog.domain.comment.business

import org.springframework.data.domain.Pageable
import software.darkmatter.school.blog.api.dto.CommentCreateDto
import software.darkmatter.school.blog.api.dto.CommentUpdateDto
import software.darkmatter.school.blog.domain.comment.data.Comment

interface CommentService {
    suspend fun getListByPostId(postId: Long, pageable: Pageable): List<Comment>
    suspend fun getById(id: Long): Comment
    suspend fun getByPostIdAndId(postId: Long, id: Long): Comment
    suspend fun create(postId: Long, createDto: CommentCreateDto): Comment
    suspend fun update(id: Long, updateDto: CommentUpdateDto): Comment
    suspend fun delete(id: Long)
}
