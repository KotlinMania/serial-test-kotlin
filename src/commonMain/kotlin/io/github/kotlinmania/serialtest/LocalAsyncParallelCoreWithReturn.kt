// port-lint: source parallel_code_lock.rs
package io.github.kotlinmania.serialtest

suspend fun localAsyncParallelCoreWithReturn(
    names: List<String>,
    path: String?,
    function: suspend () -> Result<Unit>,
): Result<Unit> {
    val locks = getLocks(names)
    locks.forEach { it.startParallel() }
    return try {
        function()
    } finally {
        locks.forEach { it.endParallel() }
    }
}
