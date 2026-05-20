// port-lint: source serial_code_lock.rs
package io.github.kotlinmania.serialtest

private fun coreInternal(names: List<String>): List<MutexGuardWrapper> =
    names.map { name ->
        checkNewKey(name)
        (globalLocks()[name] ?: error("key to be set")).lock()
    }

fun <R> localSerialCoreWithReturn(
    names: List<String>,
    path: String?,
    function: () -> Result<R>,
): Result<R> {
    val guards = coreInternal(names)
    return try {
        function()
    } finally {
        guards.asReversed().forEach { it.release() }
    }
}

fun localSerialCore(
    names: List<String>,
    path: String?,
    function: () -> Unit,
) {
    val guards = coreInternal(names)
    try {
        function()
    } finally {
        guards.asReversed().forEach { it.release() }
    }
}

suspend fun <R> localAsyncSerialCoreWithReturn(
    names: List<String>,
    path: String?,
    function: suspend () -> Result<R>,
): Result<R> {
    val guards = coreInternal(names)
    return try {
        function()
    } finally {
        guards.asReversed().forEach { it.release() }
    }
}

suspend fun localAsyncSerialCore(
    names: List<String>,
    path: String?,
    function: suspend () -> Unit,
) {
    val guards = coreInternal(names)
    try {
        function()
    } finally {
        guards.asReversed().forEach { it.release() }
    }
}
