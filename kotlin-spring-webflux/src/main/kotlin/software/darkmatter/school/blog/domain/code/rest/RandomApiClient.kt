package software.darkmatter.school.blog.domain.code.rest

import software.darkmatter.school.blog.domain.code.rest.dto.RandomResponse

interface RandomApiClient {
    suspend fun getRandom(): RandomResponse
}
