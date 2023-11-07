package software.darkmatter.school.blog.domain.post.error

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class PostAlreadyPublishedException : ResponseStatusException(HttpStatus.CONFLICT, "Post is already published")
