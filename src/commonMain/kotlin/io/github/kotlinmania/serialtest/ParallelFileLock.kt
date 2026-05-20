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

suspend fun fsAsyncParallelCoreWithReturn(
    names: List<String>,
    path: String?,
    function: suspend () -> Result<Unit>,
): Result<Unit> {
    getLocks(names, path).forEach { it.startParallel() }
    return try {
        function()
    } finally {
        getLocks(names, path).forEach { it.endParallel() }
    }
}

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
