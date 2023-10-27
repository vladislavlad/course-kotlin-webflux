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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import software.darkmatter.school.blog.api.dto.CommentCreateDto
import software.darkmatter.school.blog.api.dto.CommentDto
import software.darkmatter.school.blog.api.dto.CommentUpdateDto
import software.darkmatter.school.blog.api.dto.UserDto
import software.darkmatter.school.blog.domain.comment.business.CommentService
import software.darkmatter.school.blog.domain.comment.data.Comment

@RestController
class CommentController(
    private val service: CommentService,
) {

    @GetMapping("/posts/{postId}/comments")
    suspend fun getListByPost(
        @PathVariable postId: Long,
        @RequestParam(defaultValue = "0") page: Int?,
        @RequestParam(defaultValue = "20") size: Int?
    ): List<CommentDto> =
        service.getListByPostId(postId, Pageable.ofSize(size!!).withPage(page!!))
            .map { convertToDto(it) }

    @GetMapping("/comments/{id}")
    suspend fun getById(@PathVariable id: Long) = convertToDto(service.getById(id))

    @GetMapping("/posts/{postId}/comments/{id}")
    suspend fun getByPostIdAndId(
        @PathVariable postId: Long,
        @PathVariable id: Long
    ): CommentDto = convertToDto(service.getByPostIdAndId(postId, id))

    @PostMapping("/posts/{postId}/comments")
    suspend fun create(@PathVariable postId: Long, @Valid @RequestBody commentCreateDto: CommentCreateDto): CommentDto =
        convertToDto(service.create(postId, commentCreateDto))

    @PutMapping("/comments/{id}")
    suspend fun update(@PathVariable id: Long, @Valid @RequestBody updateDto: CommentUpdateDto): CommentDto =
        convertToDto(service.update(id, updateDto))

    @DeleteMapping("/comments/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun delete(@PathVariable id: Long) = service.delete(id)

    private fun convertToDto(comment: Comment): CommentDto {
        return CommentDto(
            id = comment.id!!,
            postId = comment.postId,
            text = comment.text,
            createdAt = comment.createdAt,
            createdBy = UserDto(
                comment.createdByUserId,
                comment.createdBy?.uuid,
                comment.createdBy?.username,
                comment.createdBy?.firstName,
                comment.createdBy?.lastName
            ),
            updatedAt = comment.updatedAt,
        )
    }
}