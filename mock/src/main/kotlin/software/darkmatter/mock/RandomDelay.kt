package software.darkmatter.mock

import kotlinx.coroutines.delay
import kotlin.random.Random

suspend fun randomDelay() {
    delay(randomMillis())
}

private fun randomMillis(): Long =
    if (Random.nextInt(1, 100) > 90) {
        Random.nextLong(100, 150)
    } else {
        Random.nextLong(5, 25)
    }
