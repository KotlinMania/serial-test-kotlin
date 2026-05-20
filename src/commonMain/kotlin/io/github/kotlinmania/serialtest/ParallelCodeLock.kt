// port-lint: source parallel_code_lock.rs
package io.github.kotlinmania.serialtest

internal fun getLocks(names: List<String>): List<UniqueReentrantMutex> =
    names.map { name ->
        checkNewKey(name)
        globalLocks()[name] ?: error("key to be set")
    }
