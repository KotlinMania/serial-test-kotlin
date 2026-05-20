// port-lint: source serial_code_lock.rs
package io.github.kotlinmania.serialtest

fun <R> localSerialCoreWithReturn(
    names: List<String>,
    path: String?,
    function: () -> Result<R>,
): Result<R> {
    val guards = serialCoreInternal(names)
    return try {
        function()
    } finally {
        guards.asReversed().forEach { it.release() }
    }
}
