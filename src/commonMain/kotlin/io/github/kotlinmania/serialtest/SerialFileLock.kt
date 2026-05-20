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
