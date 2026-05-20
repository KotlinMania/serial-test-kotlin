// port-lint: source rwlock.rs
package io.github.kotlinmania.serialtest

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
        while (true) {
            data.mutex.lock()
            if (lockState.parallels == 0 && data.serial.tryLock()) {
                data.mutex.unlock()
                return MutexGuardWrapper(data.serial, this)
            }
            data.mutex.unlock()
        }
    }

    fun startParallel() {
        while (true) {
            data.mutex.lock()
            if (lockState.parallels > 0) {
                lockState.parallels += 1
                data.mutex.unlock()
                return
            }

            if (data.serial.tryLock()) {
                data.serial.unlock()
                lockState.parallels = 1
                data.mutex.unlock()
                return
            }
            data.mutex.unlock()
        }
    }

    fun endParallel() {
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
}

internal class MutexGuardWrapper(
    private val gate: ReentrantGate,
    private val locks: Locks,
) {
    private var released = false

    fun release() {
        if (!released) {
            released = true
            gate.unlock()
        }
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
