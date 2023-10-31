package software.darkmatter.mock.controller

import kotlinx.coroutines.runBlocking
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import software.darkmatter.mock.business.CodeGenerator
import software.darkmatter.mock.dto.CodeCheckRequest
import software.darkmatter.mock.dto.CodeCreateRequest
import software.darkmatter.mock.dto.CodeCreateResponse
import software.darkmatter.mock.randomDelay
import java.util.UUID

@RestController
@RequestMapping("/api/code")
class CodeController(
    private val codeGenerator: CodeGenerator,
) {

    private val codes = mutableMapOf<UUID, String>()

    @PostMapping
    suspend fun createCode(@RequestBody request: CodeCreateRequest): CodeCreateResponse {
        val code = codeGenerator.generate()
        codes[request.userUuid] = code
        return CodeCreateResponse(code = code)
    }

    @PostMapping("/check")
    suspend fun checkCode(@RequestBody request: CodeCheckRequest): ResponseEntity<Unit> {
        runBlocking {
            randomDelay()
        }
        if (codes[request.userUuid] != request.code) {
            return ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY)
        }
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}
