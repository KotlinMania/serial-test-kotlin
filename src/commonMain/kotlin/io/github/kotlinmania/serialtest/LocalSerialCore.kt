// port-lint: source serial_code_lock.rs
package io.github.kotlinmania.serialtest

fun localSerialCore(
    names: List<String>,
    path: String?,
    function: () -> Unit,
) {
    val guards = serialCoreInternal(names)
    try {
        function()
    } finally {
        guards.asReversed().forEach { it.release() }
    }
}
