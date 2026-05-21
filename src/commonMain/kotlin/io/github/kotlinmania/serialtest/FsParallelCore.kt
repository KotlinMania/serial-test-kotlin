// port-lint: source parallel_file_lock.rs
package io.github.kotlinmania.serialtest

fun fsParallelCore(
    names: List<String>,
    path: String?,
    function: () -> Unit,
) {
    getLocks(names, path).forEach { it.startParallel() }
    try {
        function()
    } finally {
        getLocks(names, path).forEach { it.endParallel() }
    }
}
