package coroutines.playground.flow.extension

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

// https://github.com/Kotlin/kotlinx.coroutines/issues/1147
// https://medium.com/@josehhbraz/parallel-map-in-flow-kotlin-f9735c9dc237


fun main() {
    val flow = (1..10).asFlow()

}

fun <T, R> Flow<T>.parallelMap(
    context: CoroutineContext = EmptyCoroutineContext,
    transform: suspend (T) -> R
): Flow<R> {
    val scope = CoroutineScope(context + SupervisorJob())
    return map {
        scope.async { transform(it) }
    }
        .buffer()
        .map { it.await() }
        .flowOn(context)
}

