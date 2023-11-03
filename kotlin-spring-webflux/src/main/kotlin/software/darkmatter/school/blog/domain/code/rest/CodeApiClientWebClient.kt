package software.darkmatter.school.blog.domain.code.rest

import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.http.HttpStatus.OK
import org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitExchange
import reactor.core.publisher.Mono
import software.darkmatter.school.blog.domain.code.rest.dto.ApiResult
import software.darkmatter.school.blog.domain.code.rest.dto.CodeCheckRequest
import software.darkmatter.school.blog.domain.code.rest.dto.CodeCreateRequest

@Component
class CodeApiClientWebClient(
    private val webClient: WebClient
) : CodeApiClient {

    override suspend fun createCode(request: CodeCreateRequest): ApiResult =
        webClient.post()
            .uri("/api/code")
            .body(Mono.just(request), CodeCreateRequest::class.java)
            .accept(MediaType.APPLICATION_JSON)
            .awaitExchange { response ->
                when (val statusCode = response.statusCode()) {
                    NO_CONTENT, OK -> ApiResult(status = statusCode.value())
                    UNPROCESSABLE_ENTITY -> ApiResult(
                        status = statusCode.value(),
                        error = "Code not created",
                    )
                    else -> ApiResult(
                        status = statusCode.value(),
                        error = "Unknown error",
                    )
                }
            }

    override suspend fun checkCode(request: CodeCheckRequest): ApiResult =
        webClient.post()
            .uri("/api/code/check")
            .body(Mono.just(request), CodeCheckRequest::class.java)
            .accept(MediaType.APPLICATION_JSON)
            .awaitExchange { response ->
                when (val statusCode = response.statusCode()) {
                    NO_CONTENT, OK -> ApiResult(status = statusCode.value())
                    UNPROCESSABLE_ENTITY -> ApiResult(
                        status = statusCode.value(),
                        error = "Code not valid",
                    )
                    else -> ApiResult(
                        status = statusCode.value(),
                        error = "Unknown error",
                    )
                }
            }
}
