// port-lint: ignore
// Kotlin callable wrapper for the upstream crate-root file parallel attribute macro.
package io.github.kotlinmania.serialtest

/**
 * Run a test body while holding the file-backed parallel lock for the supplied
 * names or explicit path.
 *
 * File parallel bodies may run beside each other for the same lock, but they do
 * not run at the same time as a body wrapped by [fileSerial] for that lock.
 */
public fun <T> fileParallel(
    vararg names: String,
    path: String? = null,
    body: () -> T,
): T {
    var result: Result<T>? = null
    fsParallelCore(names.fileParallelNamesOrDefault(), path) {
        result = runCatching(body)
    }
    return result?.getOrThrow() ?: error("fileParallel body did not run")
}

private fun Array<out String>.fileParallelNamesOrDefault(): List<String> =
    if (isEmpty()) listOf("") else toList()
