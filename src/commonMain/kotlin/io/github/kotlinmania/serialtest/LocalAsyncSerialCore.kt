// port-lint: source serial_code_lock.rs
package io.github.kotlinmania.serialtest

suspend fun localAsyncSerialCore(
    names: List<String>,
    path: String?,
    function: suspend () -> Unit,
) {
    val guards = serialCoreInternal(names)
    try {
        function()
    } finally {
        guards.asReversed().forEach { it.release() }
    }
}
