// Kotlin callable wrapper for the upstream crate-root serial attribute macro.
// No upstream Rust counterpart (Kotlin-specific wrapper around localSerialCore).
package io.github.kotlinmania.serialtest

/**
 * Run a test body while holding the serial lock for the supplied names.
 *
 * The upstream crate exposes `serial` as a crate-root attribute. Kotlin has no
 * portable procedural-attribute mechanism, so the port keeps the crate-root
 * callable name and makes the wrapper explicit: keep the regular Kotlin test
 * annotation on the function and call `serial` around the body.
 *
 * Example:
 *
 * ```kotlin
 * @Test
 * fun testSerialOne() = serial {
 *     // Do things
 * }
 *
 * @Test
 * fun testSerialAnother() = serial("someKey") {
 *     // Do things
 * }
 * ```
 */
public fun <T> serial(
    vararg names: String,
    path: String? = null,
    body: () -> T,
): T {
    var result: Result<T>? = null
    localSerialCore(names.serialNamesOrDefault(), path) {
        result = runCatching(body)
    }
    return result?.getOrThrow() ?: error("serial body did not run")
}

private fun Array<out String>.serialNamesOrDefault(): List<String> =
    if (isEmpty()) listOf("") else toList()
