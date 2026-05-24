// port-lint: source rwlock.rs
package io.github.kotlinmania.serialtest

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RwlockTest {
    @Test
    fun serialGuardReportsLockedUntilReleased() {
        val locks = Locks.new("rwlock_serial_guard_reports_locked_until_released")

        assertFalse(locks.isLocked())

        val guard = locks.serial()
        assertTrue(locks.isLocked())

        guard.drop()
        assertFalse(locks.isLocked())
    }

    @Test
    fun parallelCountTracksActiveParallelLocks() {
        val locks = Locks.new("rwlock_parallel_count_tracks_active_parallel_locks")

        assertEquals(0, locks.parallelCount())

        locks.startParallel()
        assertEquals(1, locks.parallelCount())

        locks.startParallel()
        assertEquals(2, locks.parallelCount())

        locks.endParallel()
        assertEquals(1, locks.parallelCount())

        locks.endParallel()
        assertEquals(0, locks.parallelCount())
    }
}
