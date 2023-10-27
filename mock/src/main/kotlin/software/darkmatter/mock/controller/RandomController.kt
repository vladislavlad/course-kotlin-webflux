package software.darkmatter.mock.controller

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import software.darkmatter.mock.business.CodeGenerator
import software.darkmatter.mock.dto.RandomResponse
import java.util.UUID
import kotlin.random.Random

@RestController
@RequestMapping("/api/random")
class RandomController(
    private val codeGenerator: CodeGenerator,
) {

    @GetMapping
    suspend fun random(): RandomResponse {
        runBlocking {
            delay(randomMillis())
        }
        return RandomResponse(
            code = codeGenerator.generate(),
            uuid = UUID.randomUUID(),
        )
    }

    private fun randomMillis(): Long =
        if (Random.nextInt(1, 100) > 95) {
            Random.nextLong(50, 100)
        } else {
            Random.nextLong(5, 25)
        }
}
