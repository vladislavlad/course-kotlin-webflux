package software.darkmatter.school.blog.domain.post.business

import org.springframework.data.domain.Pageable
import software.darkmatter.school.blog.api.dto.PostCreateDto
import software.darkmatter.school.blog.api.dto.PostPublishDto
import software.darkmatter.school.blog.domain.post.data.Post

interface PostService {
    suspend fun getList(pageable: Pageable): List<Post>
    suspend fun getById(id: Long): Post
    suspend fun create(postCreateDto: PostCreateDto): Post
    suspend fun update(id: Long, postCreateDto: PostCreateDto): Post
    suspend fun delete(id: Long)
    suspend fun publish(id: Long, body: PostPublishDto)
}
