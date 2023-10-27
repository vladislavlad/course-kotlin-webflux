package software.darkmatter.mock

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MockApplication

fun main(args: Array<String>) {
    runApplication<MockApplication>(*args)
}
