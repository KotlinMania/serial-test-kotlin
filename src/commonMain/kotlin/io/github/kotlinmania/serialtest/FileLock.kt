// port-lint: source file_lock.rs
package io.github.kotlinmania.serialtest

internal class Lock private constructor(
    private val locks: Locks,
    var parallelCount: Int,
    private val path: String,
) {
    private var serialGuard: MutexGuardWrapper? = null

    fun startSerial() {
        while (parallelCount != 0) {
            parallelCount = locks.parallelCount()
        }
        serialGuard = locks.serial()
        parallelCount = locks.parallelCount()
    }

    fun endSerial() {
        serialGuard?.release()
        serialGuard = null
    }

    fun startParallel() {
        locks.startParallel()
        parallelCount = locks.parallelCount()
    }

    fun endParallel() {
        locks.endParallel()
        parallelCount = locks.parallelCount()
    }

    companion object {
        fun new(path: String): Lock {
            val locks = fileLocksForPath(path)
            return Lock(
                locks = locks,
                parallelCount = locks.parallelCount(),
                path = path,
            )
        }

        fun isLocked(path: String): Boolean = fileLocksForPath(path).isLocked()
    }
}

private object FileLockStorage {
    val mutex = SimpleSpinLock()
    val locks = mutableMapOf<String, Locks>()
}

private fun fileLocksForPath(path: String): Locks {
    FileLockStorage.mutex.lock()
    val lock = FileLockStorage.locks.getOrPut(path) { Locks(path) }
    FileLockStorage.mutex.unlock()
    return lock
}

fun pathForName(name: String): String = "serial-test-$name"

private fun makeLockForNameAndPath(name: String, path: String?): Lock =
    if (path != null) {
        Lock.new(path)
    } else {
        val defaultPath = pathForName(name)
        Lock.new(defaultPath)
    }

internal fun getLocks(names: List<String>, path: String?): List<Lock> {
    if (names.size > 1 && path != null) {
        error("Can't do file serial or parallel with both more than one name and a specific path")
    }
    return names.map { name -> makeLockForNameAndPath(name, path) }
}

/**
 * Check if the current execution is holding a file serial lock.
 *
 * Can be used to assert that a piece of code can only be called from a test
 * marked file serial.
 */
fun isLockedFileSerially(name: String? = null, path: String? = null): Boolean =
    if (path != null) {
        Lock.isLocked(path)
    } else {
        val defaultPath = pathForName(name.orEmpty())
        Lock.isLocked(defaultPath)
    }
