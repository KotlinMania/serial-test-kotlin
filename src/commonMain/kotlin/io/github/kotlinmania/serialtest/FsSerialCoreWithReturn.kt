// port-lint: source serial_file_lock.rs
package io.github.kotlinmania.serialtest

fun fsSerialCoreWithReturn(
    names: List<String>,
    path: String?,
    function: () -> Result<Unit>,
): Result<Unit> {
    val locks = getLocks(names, path)
    locks.forEach { it.startSerial() }
    return try {
        function()
    } finally {
        locks.forEach { it.endSerial() }
    }
}
