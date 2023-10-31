package software.darkmatter.school.blog.domain.code.rest

import io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT
import io.netty.handler.codec.http.HttpResponseStatus.OK
import io.netty.handler.codec.http.HttpResponseStatus.UNPROCESSABLE_ENTITY
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
            .body(request, CodeCreateRequest::class.java)
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
