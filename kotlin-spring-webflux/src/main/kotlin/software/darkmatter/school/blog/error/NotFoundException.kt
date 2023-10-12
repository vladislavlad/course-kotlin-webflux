package software.darkmatter.school.blog.error

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
open class NotFoundException(message: String) : RuntimeException(message)
