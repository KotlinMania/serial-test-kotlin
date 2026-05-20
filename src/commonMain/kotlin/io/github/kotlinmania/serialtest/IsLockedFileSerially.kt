// port-lint: source file_lock.rs
package io.github.kotlinmania.serialtest

/**
 * Check if the current execution is holding a file serial lock.
 *
 * Can be used to assert that a piece of code can only be called from a test
 * marked file serial.
 */
fun isLockedFileSerially(name: String? = null, path: String? = null): Boolean =
    isLockedFileSeriallyInternal(name, path)
