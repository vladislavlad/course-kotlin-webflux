package software.darkmatter.school.blog.domain.code.rest

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import software.darkmatter.school.blog.domain.code.rest.dto.RandomResponse

@Component
class RandomApiClientWebClient(
    private val webClient: WebClient
) : RandomApiClient {

    override suspend fun getRandom(): RandomResponse =
        webClient.get()
            .uri("/api/random")
            .retrieve()
            .bodyToMono(RandomResponse::class.java)
            .awaitSingle()
}
