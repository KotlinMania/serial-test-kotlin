// port-lint: source lib.rs
package io.github.kotlinmania.serialtest

/**
 * Run a test body while holding the parallel lock for the supplied names.
 *
 * Parallel bodies may run beside each other for the same name, but they do not
 * run at the same time as a body wrapped by [serial] for that name.
 */
public fun <T> parallel(
    vararg names: String,
    path: String? = null,
    body: () -> T,
): T {
    var result: Result<T>? = null
    localParallelCore(names.parallelNamesOrDefault(), path) {
        result = runCatching(body)
    }
    return result?.getOrThrow() ?: error("parallel body did not run")
}

private fun Array<out String>.parallelNamesOrDefault(): List<String> =
    if (isEmpty()) listOf("") else toList()
