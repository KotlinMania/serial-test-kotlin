// port-lint: source serial_file_lock.rs
package io.github.kotlinmania.serialtest

suspend fun fsAsyncSerialCoreWithReturn(
    names: List<String>,
    path: String?,
    function: suspend () -> Result<Unit>,
): Result<Unit> {
    val locks = getLocks(names, path)
    locks.forEach { it.startSerial() }
    return try {
        function()
    } finally {
        locks.forEach { it.endSerial() }
    }
}
