// port-lint: source serial_code_lock.rs
package io.github.kotlinmania.serialtest

internal fun serialCoreInternal(names: List<String>): List<MutexGuardWrapper> =
    names.map { name ->
        checkNewKey(name)
        (globalLocks()[name] ?: error("key to be set")).lock()
    }
