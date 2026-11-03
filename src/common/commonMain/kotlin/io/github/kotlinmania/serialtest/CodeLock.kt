// port-lint: source code_lock.rs
package io.github.kotlinmania.serialtest

import io.github.kotlinmania.oncecell.sync.OnceCell
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
                locks = Locks.new(name),
                id = mutexId.fetchAndAdd(1),
            )
    }
}

private val locksCell = OnceCell<MutableMap<String, UniqueReentrantMutex>>()
private val globalLocksMutex = SimpleSpinLock()

internal fun globalLocks(): MutableMap<String, UniqueReentrantMutex> =
    locksCell.getOrInit { mutableMapOf() }

/**
 * Check if the current execution is holding a serial lock.
 *
 * Can be used to assert that a piece of code can only be called
 * from a test marked with the serial helper.
 *
 * Example with serial:
 *
 * ```kotlin
 * fun doSomethingInNeedOfSerialization() {
 *     check(isLockedSerially(null))
 * }
 *
 * @Test
 * fun testSerialization() = serial {
 *     doSomethingInNeedOfSerialization()
 * }
 * ```
 *
 * Example missing serial:
 *
 * ```kotlin
 * @Test
 * fun testMissingSerialization() {
 *     check(isLockedSerially(null))
 * }
 * ```
 *
 * Example with named key:
 *
 * ```kotlin
 * @Test
 * fun testNamedSerialization() = serial("someKey") {
 *     check(isLockedSerially("someKey"))
 *     check(!isLockedSerially(null))
 * }
 * ```
 */
fun isLockedSerially(name: String? = null): Boolean {
    globalLocksMutex.lock()
    try {
        val lock = globalLocks()[name.orEmpty()]
        return lock?.isLockedByCurrentThread() ?: false
    } finally {
        globalLocksMutex.unlock()
    }
}

internal fun checkNewKey(name: String) {
    globalLocksMutex.lock()
    try {
        val map = globalLocks()
        if (!map.containsKey(name)) {
            map[name] = UniqueReentrantMutex.newMutex(name)
        }
    } finally {
        globalLocksMutex.unlock()
    }
}
