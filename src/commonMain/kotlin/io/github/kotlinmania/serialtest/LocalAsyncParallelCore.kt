// port-lint: source parallel_code_lock.rs
package io.github.kotlinmania.serialtest

suspend fun localAsyncParallelCore(
    names: List<String>,
    path: String?,
    function: suspend () -> Unit,
) {
    val locks = getLocks(names)
    locks.forEach { it.startParallel() }
    try {
        function()
    } finally {
        locks.forEach { it.endParallel() }
    }
}
