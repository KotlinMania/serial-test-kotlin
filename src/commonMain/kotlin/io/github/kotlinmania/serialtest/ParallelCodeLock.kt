// port-lint: source parallel_code_lock.rs
package io.github.kotlinmania.serialtest

private fun getLocks(names: List<String>): List<UniqueReentrantMutex> =
    names.map { name ->
        checkNewKey(name)
        globalLocks()[name] ?: error("key to be set")
    }

fun localParallelCoreWithReturn(
    names: List<String>,
    path: String?,
    function: () -> Result<Unit>,
): Result<Unit> {
    val locks = getLocks(names)
    locks.forEach { it.startParallel() }
    return try {
        function()
    } finally {
        locks.forEach { it.endParallel() }
    }
}

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
