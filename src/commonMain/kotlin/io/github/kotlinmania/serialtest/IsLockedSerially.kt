// port-lint: source code_lock.rs
package io.github.kotlinmania.serialtest

/**
 * Check if the current execution is holding a serial lock.
 *
 * Can be used to assert that a piece of code can only be called from a test
 * marked serial.
 */
fun isLockedSerially(name: String? = null): Boolean =
    isLockedSeriallyInternal(name)
