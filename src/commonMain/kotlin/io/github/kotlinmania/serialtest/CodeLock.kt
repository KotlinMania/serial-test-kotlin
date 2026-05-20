// port-lint: source code_lock.rs
package io.github.kotlinmania.serialtest

import kotlin.concurrent.atomics.AtomicInt

internal class UniqueReentrantMutex(
    private val locks: Locks,
    val id: Int,
) {
    fun lock(): MutexGuardWrapper = locks.serial()

    fun startParallel() {
        locks.startParallel()
    }

    fun endParallel() {
        locks.endParallel()
    }

    fun parallelCount(): Int = locks.parallelCount()

    fun isLocked(): Boolean = locks.isLocked()

    fun isLockedByCurrentThread(): Boolean = locks.isLockedByCurrentThread()

    companion object {
        private val mutexId = AtomicInt(1)

        fun newMutex(name: String): UniqueReentrantMutex =
            UniqueReentrantMutex(
                locks = Locks(name),
                id = mutexId.fetchAndAdd(1),
            )
    }
}

private object GlobalLocks {
    val mutex = SimpleSpinLock()
    val locks = mutableMapOf<String, UniqueReentrantMutex>()
}

internal fun globalLocks(): MutableMap<String, UniqueReentrantMutex> = GlobalLocks.locks

/**
 * Check if the current execution is holding a serial lock.
 *
 * Can be used to assert that a piece of code can only be called from a test
 * marked serial.
 */
fun isLockedSerially(name: String? = null): Boolean {
    GlobalLocks.mutex.lock()
    val lock = globalLocks()[name.orEmpty()]
    GlobalLocks.mutex.unlock()
    return lock?.isLockedByCurrentThread() ?: false
}

internal fun checkNewKey(name: String) {
    GlobalLocks.mutex.lock()
    if (!globalLocks().containsKey(name)) {
        globalLocks()[name] = UniqueReentrantMutex.newMutex(name)
    }
    GlobalLocks.mutex.unlock()
}
