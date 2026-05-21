// port-lint: source serial_file_lock.rs
package io.github.kotlinmania.serialtest

fun fsSerialCore(
    names: List<String>,
    path: String?,
    function: () -> Unit,
) {
    check(names.isNotEmpty())
    val locks = getLocks(names, path)
    locks.forEach { it.startSerial() }
    try {
        function()
    } finally {
        locks.forEach { it.endSerial() }
    }
}
