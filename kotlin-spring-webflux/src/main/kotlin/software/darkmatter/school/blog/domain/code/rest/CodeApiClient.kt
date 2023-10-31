package software.darkmatter.school.blog.domain.code.rest

import software.darkmatter.school.blog.domain.code.rest.dto.ApiResult
import software.darkmatter.school.blog.domain.code.rest.dto.CodeCheckRequest
import software.darkmatter.school.blog.domain.code.rest.dto.CodeCreateRequest

interface CodeApiClient {

    suspend fun createCode(request: CodeCreateRequest): ApiResult

    suspend fun checkCode(request: CodeCheckRequest): ApiResult
}
