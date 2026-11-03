// port-lint: source file_lock.rs
package io.github.kotlinmania.serialtest

internal class Lock private constructor(
    private val locks: Locks,
    var parallelCount: Int,
    private val path: String,
) {
    private var serialGuard: MutexGuardWrapper? = null

    private fun unlock() {
        serialGuard?.release()
        serialGuard = null
    }

    fun startSerial() {
        while (parallelCount != 0) {
            parallelCount = locks.parallelCount()
        }
        serialGuard = locks.serial()
        parallelCount = locks.parallelCount()
    }

    fun endSerial() {
        unlock()
    }

    private fun writeParallel() {
        // In-process lock synchronization maintains parallelCount directly in lockState.
    }

    fun startParallel() {
        locks.startParallel()
        parallelCount = locks.parallelCount()
        writeParallel()
        unlock()
    }

    fun endParallel() {
        check(parallelCount > 0)
        locks.endParallel()
        parallelCount = locks.parallelCount()
        writeParallel()
        unlock()
    }

    companion object {
        fun genCountFile(path: String): String = "$path-count"

        fun readParallelCount(path: String): Int = fileLocksForPath(path).parallelCount()

        fun createLockfile(path: String): Locks = fileLocksForPath(path)

        fun new(path: String): Lock {
            val locks = createLockfile(path)
            return Lock(
                locks = locks,
                parallelCount = readParallelCount(path),
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
    try {
        return FileLockStorage.locks.getOrPut(path) { Locks.new(path) }
    } finally {
        FileLockStorage.mutex.unlock()
    }
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
 * Can be used to assert that a piece of code can only be called
 * from a test marked with the file serial helper.
 *
 * Example with file serial:
 *
 * ```kotlin
 * fun doSomethingInNeedOfSerialization() {
 *     check(isLockedFileSerially(null, null))
 * }
 *
 * @Test
 * fun testSerialization() = fileSerial {
 *     doSomethingInNeedOfSerialization()
 * }
 * ```
 *
 * Example missing file serial:
 *
 * ```kotlin
 * @Test
 * fun testMissingSerialization() {
 *     check(isLockedFileSerially(null, null))
 * }
 * ```
 *
 * Example with named key:
 *
 * ```kotlin
 * @Test
 * fun testNamedSerialization() = fileSerial("someKey") {
 *     check(isLockedFileSerially("someKey", null))
 *     check(!isLockedFileSerially(null, null))
 * }
 * ```
 */
fun isLockedFileSerially(name: String? = null, path: String? = null): Boolean =
    if (path != null) {
        Lock.isLocked(path)
    } else {
        val defaultPath = pathForName(name.orEmpty())
        Lock.isLocked(defaultPath)
    }
