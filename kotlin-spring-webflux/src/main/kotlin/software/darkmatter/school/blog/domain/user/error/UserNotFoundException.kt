package software.darkmatter.school.blog.domain.user.error

import software.darkmatter.school.blog.error.NotFoundException

class UserNotFoundException(val userId: Long) : NotFoundException("User with id '$userId' was not found.")
