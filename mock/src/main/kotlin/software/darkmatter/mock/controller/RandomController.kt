package software.darkmatter.mock.controller

import kotlinx.coroutines.runBlocking
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import software.darkmatter.mock.business.CodeGenerator
import software.darkmatter.mock.dto.RandomResponse
import software.darkmatter.mock.randomDelay
import java.util.UUID

@RestController
@RequestMapping("/api/random")
class RandomController(
    private val codeGenerator: CodeGenerator,
) {

    @GetMapping
    suspend fun random(): RandomResponse {
        runBlocking {
            randomDelay()
        }
        return RandomResponse(
            code = codeGenerator.generate(),
            uuid = UUID.randomUUID(),
        )
    }
}
