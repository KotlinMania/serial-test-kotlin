// port-lint: source serial_code_lock.rs
package io.github.kotlinmania.serialtest

internal fun serialCoreInternal(names: List<String>): List<MutexGuardWrapper> =
    names.map { name ->
        checkNewKey(name)
        (globalLocks()[name] ?: error("key to be set")).lock()
    }

fun <R> localSerialCoreWithReturn(
    names: List<String>,
    path: String? = null,
    function: () -> Result<R>,
): Result<R> {
    val guards = serialCoreInternal(names)
    return try {
        function()
    } finally {
        guards.asReversed().forEach { it.release() }
    }
}

fun localSerialCore(
    names: List<String>,
    path: String? = null,
    function: () -> Unit,
) {
    val guards = serialCoreInternal(names)
    try {
        function()
    } finally {
        guards.asReversed().forEach { it.release() }
    }
}

suspend fun <R> localAsyncSerialCoreWithReturn(
    names: List<String>,
    path: String? = null,
    function: suspend () -> Result<R>,
): Result<R> {
    val guards = serialCoreInternal(names)
    return try {
        function()
    } finally {
        guards.asReversed().forEach { it.release() }
    }
}

suspend fun localAsyncSerialCore(
    names: List<String>,
    path: String? = null,
    function: suspend () -> Unit,
) {
    val guards = serialCoreInternal(names)
    try {
        function()
    } finally {
        guards.asReversed().forEach { it.release() }
    }
}
