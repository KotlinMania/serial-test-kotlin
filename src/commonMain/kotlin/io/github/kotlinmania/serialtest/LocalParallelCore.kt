// port-lint: source parallel_code_lock.rs
package io.github.kotlinmania.serialtest

fun localParallelCore(
    names: List<String>,
    path: String?,
    function: () -> Unit,
) {
    val locks = getLocks(names)
    locks.forEach { it.startParallel() }
    try {
        function()
    } finally {
        locks.forEach { it.endParallel() }
    }
}
