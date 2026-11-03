// port-lint: source parallel_file_lock.rs
package io.github.kotlinmania.serialtest

fun fsParallelCore(
    names: List<String>,
    path: String? = null,
    function: () -> Unit,
) {
    val locks = getLocks(names, path)
    locks.forEach { it.startParallel() }
    try {
        function()
    } finally {
        locks.forEach { it.endParallel() }
    }
}

fun fsParallelCoreWithReturn(
    names: List<String>,
    path: String? = null,
    function: () -> Result<Unit>,
): Result<Unit> {
    val locks = getLocks(names, path)
    locks.forEach { it.startParallel() }
    return try {
        function()
    } finally {
        locks.forEach { it.endParallel() }
    }
}

suspend fun fsAsyncParallelCoreWithReturn(
    names: List<String>,
    path: String? = null,
    function: suspend () -> Result<Unit>,
): Result<Unit> {
    val locks = getLocks(names, path)
    locks.forEach { it.startParallel() }
    return try {
        function()
    } finally {
        locks.forEach { it.endParallel() }
    }
}

suspend fun fsAsyncParallelCore(
    names: List<String>,
    path: String? = null,
    function: suspend () -> Unit,
) {
    val locks = getLocks(names, path)
    locks.forEach { it.startParallel() }
    try {
        function()
    } finally {
        locks.forEach { it.endParallel() }
    }
}
