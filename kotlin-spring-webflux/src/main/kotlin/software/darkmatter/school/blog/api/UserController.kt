package software.darkmatter.school.blog.api

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import software.darkmatter.school.blog.api.dto.UserCreateDto
import software.darkmatter.school.blog.api.dto.UserDto
import software.darkmatter.school.blog.domain.user.business.UserService
import software.darkmatter.school.blog.domain.user.domain.User

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @GetMapping("/{id}")
    suspend fun getById(@PathVariable id: Long) = convertToDto(userService.getById(id))

    @PostMapping
    suspend fun create(@Valid @RequestBody userCreateDto: UserCreateDto) =
        convertToDto(userService.create(userCreateDto))

    @PutMapping("/{id}")
    suspend fun update(
        @PathVariable id: Long, @Valid @RequestBody userCreateDto: UserCreateDto
    ) = convertToDto(userService.update(id, userCreateDto))

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun delete(@PathVariable id: Long) = userService.delete(id)

    private fun convertToDto(user: User): UserDto {
        return UserDto(
            id = user.id!!,
            uuid = user.uuid,
            username = user.username,
            firstName = user.firstName,
            lastName = user.lastName,
        )
    }
}
