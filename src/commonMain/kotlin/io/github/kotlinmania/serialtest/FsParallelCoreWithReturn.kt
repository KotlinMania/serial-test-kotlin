// port-lint: source parallel_file_lock.rs
package io.github.kotlinmania.serialtest

fun fsParallelCoreWithReturn(
    names: List<String>,
    path: String?,
    function: () -> Result<Unit>,
): Result<Unit> {
    getLocks(names, path).forEach { it.startParallel() }
    return try {
        function()
    } finally {
        getLocks(names, path).forEach { it.endParallel() }
    }
}
