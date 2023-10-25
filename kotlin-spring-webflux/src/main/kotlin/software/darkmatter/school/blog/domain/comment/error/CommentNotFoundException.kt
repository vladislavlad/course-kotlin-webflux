package software.darkmatter.school.blog.domain.comment.error

import software.darkmatter.school.blog.error.NotFoundException

class CommentNotFoundException(val commentId: Long) : NotFoundException("Comment with id '$commentId' was not found.")
