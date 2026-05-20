// port-lint: source serial_file_lock.rs
package io.github.kotlinmania.serialtest

suspend fun fsAsyncSerialCore(
    names: List<String>,
    path: String?,
    function: suspend () -> Unit,
) {
    val locks = getLocks(names, path)
    locks.forEach { it.startSerial() }
    try {
        function()
    } finally {
        locks.forEach { it.endSerial() }
    }
}
