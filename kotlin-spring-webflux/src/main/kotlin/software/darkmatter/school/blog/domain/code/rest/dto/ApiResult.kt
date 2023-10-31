package software.darkmatter.school.blog.domain.code.rest.dto

data class ApiResult(
    val status: Int,
    val error: String? = null,
)
