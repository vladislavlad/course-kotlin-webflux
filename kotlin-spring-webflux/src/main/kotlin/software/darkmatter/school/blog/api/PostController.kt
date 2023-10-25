package software.darkmatter.school.blog.api

import jakarta.validation.Valid
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import software.darkmatter.school.blog.api.dto.PostCreateDto
import software.darkmatter.school.blog.api.dto.PostDto
import software.darkmatter.school.blog.api.dto.UserDto
import software.darkmatter.school.blog.domain.post.business.PostService
import software.darkmatter.school.blog.domain.post.data.Post

@RestController
@RequestMapping("/posts")
class PostController(
    private val service: PostService,
) {

    @GetMapping
    suspend fun list(
        @RequestParam(defaultValue = "0") page: Int?,
        @RequestParam(defaultValue = "20") size: Int?
    ): List<PostDto> {
        return service.getList(Pageable.ofSize(size!!).withPage(page!!))
            .map { post -> convertToDto(post) }
    }

    @GetMapping("/{id}")
    suspend fun get(@PathVariable id: Long): PostDto {
        return convertToDto(service.getById(id))
    }

    @PostMapping
    suspend fun create(@RequestBody body: @Valid PostCreateDto): PostDto {
        return convertToDto(service.create(body))
    }

    @PutMapping("/{id}")
    suspend fun update(@PathVariable id: Long, @RequestBody body: @Valid PostCreateDto): PostDto {
        return convertToDto(service.update(id, body))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    suspend fun delete(@PathVariable id: Long) {
        service.delete(id)
    }

    @PostMapping("/{id}/publish")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    suspend fun publish(@PathVariable id: Long) {
        service.publish(id)
    }

    private fun convertToDto(post: Post): PostDto {
        return PostDto(
            id = post.id!!,
            title = post.title,
            summary = post.summary,
            content = post.content,
            createdAt = post.createdAt,
            createdBy = UserDto(
                post.createdByUserId,
                post.createdBy?.username,
                post.createdBy?.firstName,
                post.createdBy?.lastName,
            ),
            updatedAt = post.updatedAt,
            publishedAt = post.publishedAt,
        )
    }
}
