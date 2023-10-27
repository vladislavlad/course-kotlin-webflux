package software.darkmatter.mock.business

import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class CodeGeneratorImpl : CodeGenerator {
    override fun generate(): String = Random.nextInt(1000000, 1999999).toString().slice(1..6)
}
