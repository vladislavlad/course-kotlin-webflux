package software.darkmatter.school.blog.domain.post.error

import software.darkmatter.school.blog.error.NotFoundException

class PostNotFoundException(val postId: Long) : NotFoundException("Post with id '$postId' was not found.")
