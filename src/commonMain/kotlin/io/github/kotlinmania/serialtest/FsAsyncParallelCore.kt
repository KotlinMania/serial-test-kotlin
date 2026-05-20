// port-lint: source parallel_file_lock.rs
package io.github.kotlinmania.serialtest

suspend fun fsAsyncParallelCore(
    names: List<String>,
    path: String?,
    function: suspend () -> Unit,
) {
    getLocks(names, path).forEach { it.startParallel() }
    try {
        function()
    } finally {
        getLocks(names, path).forEach { it.endParallel() }
    }
}
