// port-lint: ignore
// Kotlin callable wrapper for the upstream crate-root file serial attribute macro.
package io.github.kotlinmania.serialtest

/**
 * Run a test body while holding the file-backed serial lock for the supplied
 * names or explicit path.
 *
 * This is the explicit Kotlin wrapper for the upstream crate-root file serial
 * attribute. It gives separate-process test shapes the same serial exclusion
 * point as [fsSerialCore].
 */
public fun <T> fileSerial(
    vararg names: String,
    path: String? = null,
    body: () -> T,
): T {
    var result: Result<T>? = null
    fsSerialCore(names.fileSerialNamesOrDefault(), path) {
        result = runCatching(body)
    }
    return result?.getOrThrow() ?: error("fileSerial body did not run")
}

private fun Array<out String>.fileSerialNamesOrDefault(): List<String> =
    if (isEmpty()) listOf("") else toList()
