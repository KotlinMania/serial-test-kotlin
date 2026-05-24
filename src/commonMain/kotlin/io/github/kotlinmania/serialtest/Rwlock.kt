// port-lint: source rwlock.rs
package io.github.kotlinmania.serialtest

import io.github.kotlinmania.log.Arguments
import io.github.kotlinmania.log.debug
import kotlin.concurrent.atomics.AtomicInt

internal class LockState(
    var parallels: Int,
)

internal class LockData(
    val mutex: SimpleSpinLock,
    val serial: ReentrantGate,
)

internal class Locks private constructor(
    val data: LockData,
    val name: String,
) {
    constructor(name: String) : this(
        LockData(
            mutex = SimpleSpinLock(),
            serial = ReentrantGate(),
        ),
        name,
    )

    private val lockState = LockState(parallels = 0)

    fun isLocked(): Boolean = data.serial.isLocked()

    fun isLockedByCurrentThread(): Boolean = data.serial.isLocked()

    fun serial(): MutexGuardWrapper {
        debug(Arguments("Get serial lock '{}'", listOf(name)))
        while (true) {
            data.mutex.lock()
            debug(Arguments("Serial acquire {} {}", listOf(lockState.parallels, name)))
            if (lockState.parallels == 0 && data.serial.tryLock()) {
                debug(Arguments("Got serial '{}'", listOf(name)))
                data.mutex.unlock()
                return MutexGuardWrapper(data.serial, this, name)
            }
            debug(Arguments("Someone else has serial '{}'", listOf(name)))
            data.mutex.unlock()
        }
    }

    fun startParallel() {
        debug(Arguments("Get parallel lock '{}'", listOf(name)))
        while (true) {
            data.mutex.lock()
            debug(Arguments("Parallel, existing {} '{}'", listOf(lockState.parallels, name)))
            if (lockState.parallels > 0) {
                lockState.parallels += 1
                data.mutex.unlock()
                return
            }

            if (data.serial.tryLock()) {
                debug(Arguments("Parallel first '{}'", listOf(name)))
                data.serial.unlock()
                lockState.parallels = 1
                data.mutex.unlock()
                return
            }
            debug(Arguments("Parallel waiting '{}'", listOf(name)))
            data.mutex.unlock()
        }
    }

    fun endParallel() {
        debug(Arguments("End parallel '{}", listOf(name)))
        data.mutex.lock()
        check(lockState.parallels > 0)
        lockState.parallels -= 1
        data.mutex.unlock()
    }

    fun parallelCount(): Int {
        data.mutex.lock()
        val count = lockState.parallels
        data.mutex.unlock()
        return count
    }

    companion object {
        fun new(name: String): Locks = Locks(name)
    }
}

internal class MutexGuardWrapper(
    private val gate: ReentrantGate,
    private val locks: Locks,
    private val name: String,
) {
    private var released = false

    fun release() {
        if (!released) {
            released = true
            debug(Arguments("End serial", emptyList()))
            gate.unlock()
        }
    }

    fun drop() {
        release()
    }
}

internal class SimpleSpinLock {
    private val state = AtomicInt(0)

    fun lock() {
        while (!state.compareAndSet(expectedValue = 0, newValue = 1)) {
        }
    }

    fun unlock() {
        state.store(0)
    }
}

internal class ReentrantGate {
    private val state = AtomicInt(0)

    fun tryLock(): Boolean =
        state.compareAndSet(expectedValue = 0, newValue = 1)

    fun unlock() {
        state.store(0)
    }

    fun isLocked(): Boolean = state.load() != 0
}
